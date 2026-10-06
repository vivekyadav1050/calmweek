package com.example.calmweek.model

enum class ResourceType {
    VIDEO, WEBSITE, NOTE, PRACTICE
}

data class Resource(
    val resourceId: String = "",
    val subjectId: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val thumbnailUrl: String = "",
    val provider: String = "",
    val channelName: String = "",
    val unit: String = "Unit 1",
    val difficulty: String = "Beginner",
    val type: ResourceType = ResourceType.VIDEO,
    val category: String = "",
    val fileUrl: String = "",
    val isRecommended: Boolean = false,
    val isCompleted: Boolean = false
)

data class YouTubeChannel(
    val channelId: String = "",
    val name: String = "",
    val description: String = "",
    val logoUrl: String = "",
    val youtubeUrl: String = "",
    val subjects: List<String> = emptyList(),
    val isRecommended: Boolean = true
)
