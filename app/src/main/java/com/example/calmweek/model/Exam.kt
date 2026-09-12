package com.example.calmweek.model

data class Exam(
    val examId: String = "",
    val userId: String = "",
    val subjectId: String = "",
    val subjectName: String = "",
    val examDate: Long = System.currentTimeMillis() + 864000000L,
    val notes: String = "",
    val progress: Int = 0
)
