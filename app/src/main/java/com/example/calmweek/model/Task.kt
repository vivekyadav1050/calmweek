package com.example.calmweek.model

data class Task(
    val taskId: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val subjectId: String = "",
    val priority: String = "Medium", // Low, Medium, High
    val deadline: Long = System.currentTimeMillis() + 86400000L,
    val estimatedMinutes: Int = 30,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
