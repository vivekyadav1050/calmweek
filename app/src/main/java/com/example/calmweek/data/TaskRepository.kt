package com.example.calmweek.data

import com.example.calmweek.model.Task

class TaskRepository {
    private val tasksList = mutableListOf(
        Task("t1", "user1", "Complete OS CPU Scheduling Assignment", "Solve problems on SJF and Round Robin", "os", "High", System.currentTimeMillis() + 3600000L * 5, 45, false),
        Task("t2", "user1", "Revise DBMS Normalization", "1NF, 2NF, 3NF and BCNF concepts", "dbms", "Medium", System.currentTimeMillis() + 3600000L * 24, 30, true),
        Task("t3", "user1", "Computer Networks OSI Layers", "Review physical and data link layers", "cn", "Low", System.currentTimeMillis() + 3600000L * 48, 25, false)
    )

    fun getTasks(): List<Task> = tasksList

    fun addTask(task: Task) {
        tasksList.add(0, task)
    }

    fun toggleTask(taskId: String) {
        val index = tasksList.indexOfFirst { it.taskId == taskId }
        if (index != -1) {
            val t = tasksList[index]
            tasksList[index] = t.copy(completed = !t.completed)
        }
    }

    fun deleteTask(taskId: String) {
        tasksList.removeAll { it.taskId == taskId }
    }
}
