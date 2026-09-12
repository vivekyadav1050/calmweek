package com.example.calmweek.model

data class Progress(
    val progressId: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val percentage: Int = 0,
    val completedResources: Int = 0,
    val totalResources: Int = 0,
    val lastStudied: Long = System.currentTimeMillis()
)
