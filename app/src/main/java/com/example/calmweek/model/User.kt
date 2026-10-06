package com.example.calmweek.model

data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val course: String = "BTech CSE",
    val semester: String = "Semester 1",
    val studyGoal: String = "Improve academics",
    val profileImageUrl: String = "",
    val streak: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
