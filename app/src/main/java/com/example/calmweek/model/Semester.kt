package com.example.calmweek.model

data class Semester(
    val semesterId: String = "",
    val name: String = "",
    val order: Int = 1,
    val subjectCount: Int = 0,
    val progress: Int = 0
)

data class Subject(
    val subjectId: String = "",
    val name: String = "",
    val semesterId: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val order: Int = 1,
    val isActive: Boolean = true,
    val progress: Int = 0,
    val resourceCount: Int = 0
)
