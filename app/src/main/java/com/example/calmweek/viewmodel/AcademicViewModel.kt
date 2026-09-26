package com.example.calmweek.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calmweek.data.AcademicRepository
import com.example.calmweek.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AcademicViewModel : ViewModel() {
    private val repository = AcademicRepository()
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    private val _semesters = MutableStateFlow(repository.getSemesters())
    val semesters: StateFlow<List<Semester>> = _semesters.asStateFlow()

    private val _subjectsMap = MutableStateFlow<Map<String, List<Subject>>>(emptyMap())
    private val _resourcesMap = MutableStateFlow<Map<String, List<Resource>>>(emptyMap())

    init {
        loadUserProgressFromFirebase()
    }

    private fun loadUserProgressFromFirebase() {
        val uid = auth.currentUser?.uid ?: return
        database.child("users").child(uid).child("academic").get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val subjectsSnapshot = snapshot.child("subjects")
                val completedResourceMap = mutableMapOf<String, MutableSet<String>>()

                for (subSnap in subjectsSnapshot.children) {
                    val subId = subSnap.key ?: continue
                    val resSnap = subSnap.child("resources")
                    val set = mutableSetOf<String>()
                    for (r in resSnap.children) {
                        val rId = r.key ?: continue
                        val done = r.getValue(Boolean::class.java) ?: false
                        if (done) set.add(rId)
                    }
                    completedResourceMap[subId] = set
                }

                val semestersList = repository.getSemesters().toMutableList()
                val updatedSubjectsMap = mutableMapOf<String, List<Subject>>()
                val updatedResourcesMap = mutableMapOf<String, List<Resource>>()

                for (sem in semestersList) {
                    val subjects = repository.getSubjectsForSemester(sem.semesterId)
                    val updatedSubjects = subjects.map { sub ->
                        val resources = repository.getResourcesForSubject(sub.subjectId)
                        val completedSet = completedResourceMap[sub.subjectId] ?: emptySet()
                        val updatedResources = resources.map { res ->
                            res.copy(isCompleted = completedSet.contains(res.resourceId))
                        }
                        updatedResourcesMap[sub.subjectId] = updatedResources

                        val completedCount = updatedResources.count { it.isCompleted }
                        val total = updatedResources.size
                        val subProgress = if (total > 0) (completedCount * 100) / total else 0
                        sub.copy(progress = subProgress, resourceCount = total)
                    }
                    updatedSubjectsMap[sem.semesterId] = updatedSubjects

                    val semSubjects = updatedSubjectsMap[sem.semesterId] ?: emptyList()
                    val avgProgress = if (semSubjects.isNotEmpty()) {
                        semSubjects.sumOf { it.progress } / semSubjects.size
                    } else 0
                    val semIndex = semestersList.indexOfFirst { it.semesterId == sem.semesterId }
                    if (semIndex != -1) {
                        semestersList[semIndex] = semestersList[semIndex].copy(progress = avgProgress)
                    }
                }

                _semesters.value = semestersList
                _subjectsMap.value = updatedSubjectsMap
                _resourcesMap.value = updatedResourcesMap
            }
        }
    }

    fun getSubjects(semesterId: String): List<Subject> {
        val current = _subjectsMap.value[semesterId]
        if (current != null) return current
        val initial = repository.getSubjectsForSemester(semesterId)
        _subjectsMap.value = _subjectsMap.value + (semesterId to initial)
        return initial
    }

    fun getResources(subjectId: String): List<Resource> {
        val current = _resourcesMap.value[subjectId]
        if (current != null) return current
        val initial = repository.getResourcesForSubject(subjectId)
        _resourcesMap.value = _resourcesMap.value + (subjectId to initial)
        return initial
    }

    fun toggleResourceCompletion(semesterId: String, subjectId: String, resourceId: String) {
        val resources = getResources(subjectId).toMutableList()
        val index = resources.indexOfFirst { it.resourceId == resourceId }
        if (index != -1) {
            val newIsCompleted = !resources[index].isCompleted
            resources[index] = resources[index].copy(isCompleted = newIsCompleted)
            _resourcesMap.value = _resourcesMap.value + (subjectId to resources)

            val completed = resources.count { it.isCompleted }
            val total = resources.size
            val subjectProgress = if (total > 0) (completed * 100) / total else 0

            val subjects = getSubjects(semesterId).toMutableList()
            val subIndex = subjects.indexOfFirst { it.subjectId == subjectId }
            if (subIndex != -1) {
                subjects[subIndex] = subjects[subIndex].copy(progress = subjectProgress, resourceCount = total)
                _subjectsMap.value = _subjectsMap.value + (semesterId to subjects)
            }

            val semIndex = _semesters.value.indexOfFirst { it.semesterId == semesterId }
            var semProgress = 0
            if (semIndex != -1) {
                val semList = _semesters.value.toMutableList()
                val semSubjects = getSubjects(semesterId)
                semProgress = if (semSubjects.isNotEmpty()) {
                    semSubjects.sumOf { it.progress } / semSubjects.size
                } else 0
                semList[semIndex] = semList[semIndex].copy(progress = semProgress)
                _semesters.value = semList
            }

            // Save to Firebase Realtime Database with separate scoping per subjectId
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val subRef = database.child("users").child(uid).child("academic").child("subjects").child(subjectId)
                subRef.child("resources").child(resourceId).setValue(newIsCompleted)
                subRef.child("progress").setValue(subjectProgress)
                database.child("users").child(uid).child("academic").child("semesters").child(semesterId).child("progress").setValue(semProgress)
            }
        }
    }

    fun getChannels(): List<YouTubeChannel> {
        return repository.getRecommendedChannels()
    }
}
