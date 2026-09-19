package com.example.calmweek.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ProfileSetup : Screen("profile_setup")
    //priavte val latest= AcademicViewModel

    // Bottom Nav Destinations
    object Home : Screen("home")
    object Learn : Screen("learn")
    object Calm : Screen("calm")
    object Focus : Screen("focus")
    object Profile : Screen("profile")

    // Sub-screens
    object SemesterDetail : Screen("semester_detail/{semesterId}") {
        fun createRoute(semesterId: String) = "semester_detail/$semesterId"
    }
    object SubjectDetail : Screen("subject_detail/{subjectId}") {
        fun createRoute(subjectId: String) = "subject_detail/$subjectId"
    }
    object Breathing : Screen("breathing")
    object Grounding : Screen("grounding")
    object MoodCheckIn : Screen("mood_checkin")
    object Tasks : Screen("tasks")
}
