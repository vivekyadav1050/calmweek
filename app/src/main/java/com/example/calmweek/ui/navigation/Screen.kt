package com.example.calmweek.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ProfileSetup : Screen("profile_setup")

    // Bottom Nav Destinations
    object Home : Screen("home")
    object Learn : Screen("learn")
    object Calm : Screen("calm")
    object Focus : Screen("focus")
    object AIChat : Screen("ai_chat")
    object Profile : Screen("profile")

    // Sub-screens
    object SemesterDetail : Screen("semester_detail/{semesterId}") {
        fun createRoute(semesterId: String) = "semester_detail/$semesterId"
    }
    object SubjectDetail : Screen("subject_detail/{semesterId}/{subjectId}") {
        fun createRoute(semesterId: String, subjectId: String) = "subject_detail/$semesterId/$subjectId"
    }
    object Breathing : Screen("breathing")
    object Grounding : Screen("grounding")
    object MoodCheckIn : Screen("mood_checkin")
    object Tasks : Screen("tasks")
    object MoodSupport : Screen("mood_support/{mood}") {
        fun createRoute(mood: String) = "mood_support/$mood"
    }
    object Habits : Screen("habits")
    object Journal : Screen("journal")
    object AmbientSounds : Screen("ambient_sounds")
    object MoodAnalytics : Screen("mood_analytics")
    object WeeklyReport : Screen("weekly_report")
    object Mindfulness : Screen("mindfulness")
}
