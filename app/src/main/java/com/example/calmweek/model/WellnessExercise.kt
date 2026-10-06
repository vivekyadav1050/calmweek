package com.example.calmweek.model

data class WellnessExercise(
    val exerciseId: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = "Breathing", // Breathing, Grounding, Relax
    val durationMinutes: Int = 2,
    val instructions: List<String> = emptyList(),
    val imageUrl: String = ""
)
