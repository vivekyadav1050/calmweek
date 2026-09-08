package com.example.calmweek.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calmweek.data.TaskRepository
import com.example.calmweek.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow(repository.getTasks())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    //priavte val latest= AcademicViewModel


    fun addTask(title: String, description: String, priority: String, estimatedMinutes: Int) {
        val newTask = Task(
            taskId = System.currentTimeMillis().toString(),
            title = title,
            description = description,
            priority = priority,
            estimatedMinutes = estimatedMinutes
        )
        repository.addTask(newTask)
        _tasks.value = repository.getTasks().toList()
    }

    fun toggleTask(taskId: String) {
        repository.toggleTask(taskId)
        _tasks.value = repository.getTasks().toList()
    }

    fun deleteTask(taskId: String) {
        repository.deleteTask(taskId)
        _tasks.value = repository.getTasks().toList()
    }
}
