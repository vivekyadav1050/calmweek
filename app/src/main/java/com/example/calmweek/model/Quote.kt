package com.example.calmweek.model

data class Quote(
    val quoteId: String = "",
    val text: String = "Small progress is still progress.",
    val author: String = "Calm Week",
    val active: Boolean = true
)
