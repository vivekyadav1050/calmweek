package com.example.calmweek.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calmweek.data.AcademicRepository
import com.example.calmweek.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AcademicViewModel : ViewModel() {
    private val repository = AcademicRepository()
//priavte val latest= AcademicViewModel
    private val _semesters = MutableStateFlow(repository.getSemesters())
    val semesters: StateFlow<List<Semester>> = _semesters.asStateFlow()

    fun getSubjects(semesterId: String): List<Subject> {
        return repository.getSubjectsForSemester(semesterId)
    }



    fun getResources(subjectId: String): List<Resource> {
        return repository.getResourcesForSubject(subjectId)
    }

    fun getChannels(): List<YouTubeChannel> {
        return repository.getRecommendedChannels()
    }
}
