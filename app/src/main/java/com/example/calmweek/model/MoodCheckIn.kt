package com.example.calmweek.model

data class MoodCheckIn(
    val checkInId: String = "",
    val userId: String = "",
    val mood: Int = 3, // 1-5
    val stress: Int = 3, // 1-5
    val sleep: String = "Good", // Poor, Okay, Good
    val energy: String = "Medium", // Low, Medium, High
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
