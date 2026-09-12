package com.example.calmweek.model

data class StudySession(
    val sessionId: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val durationMinutes: Int = 25,
    val type: String = "Pomodoro", // Pomodoro, Short Break, Long Break
    val completed: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)
