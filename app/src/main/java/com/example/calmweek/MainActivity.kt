package com.example.calmweek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.calmweek.ui.navigation.Screen
import com.example.calmweek.ui.screens.*
import com.example.calmweek.ui.theme.CalmWeekTheme
import com.example.calmweek.viewmodel.AcademicViewModel
import com.example.calmweek.viewmodel.AuthViewModel
import com.example.calmweek.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalmWeekTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val academicViewModel: AcademicViewModel = viewModel()
                val taskViewModel: TaskViewModel = viewModel()

                val startRoute = if (authViewModel.isLoggedIn) Screen.Home.route else Screen.Splash.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val showBottomBar = currentRoute in listOf(
                            Screen.Home.route,
                            Screen.Learn.route,
                            Screen.Calm.route,
                            Screen.Focus.route,
                            Screen.Profile.route
                        )

                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 8.dp
                            ) {
                                val items = listOf(
                                    Triple("Home", Screen.Home.route, Icons.Default.Home),
                                    Triple("Learn", Screen.Learn.route, Icons.Default.Book),
                                    Triple("Calm", Screen.Calm.route, Icons.Default.Spa),
                                    Triple("Focus", Screen.Focus.route, Icons.Default.Timer),
                                    Triple("Profile", Screen.Profile.route, Icons.Default.Person)
                                )

                                items.forEach { (label, route, icon) ->
                                    NavigationBarItem(
                                        icon = { Icon(imageVector = icon, contentDescription = label) },
                                        label = { Text(label) },
                                        selected = currentRoute == route,
                                        onClick = {
                                            if (currentRoute != route) {
                                                navController.navigate(route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onSplashFinished = {
                                    val next = if (authViewModel.isLoggedIn) Screen.Home.route else Screen.Login.route
                                    navController.navigate(next) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel = authViewModel,
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                },
                                onNavigateToSignup = {
                                    navController.navigate(Screen.Signup.route)
                                }
                            )
                        }
                        composable(Screen.Signup.route) {
                            SignupScreen(
                                viewModel = authViewModel,
                                onSignupSuccess = {
                                    navController.navigate(Screen.ProfileSetup.route) {
                                        popUpTo(Screen.Signup.route) { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Screen.ProfileSetup.route) {
                            ProfileSetupScreen(
                                viewModel = authViewModel,
                                onProfileSetupComplete = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.ProfileSetup.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToLearn = { navController.navigate(Screen.Learn.route) },
                                onNavigateToCalm = { navController.navigate(Screen.Calm.route) },
                                onNavigateToFocus = { navController.navigate(Screen.Focus.route) },
                                onNavigateToTasks = { navController.navigate(Screen.Tasks.route) }
                            )
                        }
                        composable(Screen.Learn.route) {
                            LearnScreen(
                                viewModel = academicViewModel,
                                onSemesterClick = { semesterId ->
                                    navController.navigate(Screen.SemesterDetail.createRoute(semesterId))
                                }
                            )
                        }
                        composable(Screen.Calm.route) {
                            CalmScreen(
                                onNavigateToBreathing = { navController.navigate(Screen.Breathing.route) },
                                onNavigateToGrounding = { navController.navigate(Screen.Grounding.route) },
                                onNavigateToMood = { navController.navigate(Screen.MoodCheckIn.route) }
                            )
                        }
                        composable(Screen.Focus.route) {
                            FocusScreen(
                                onNavigateToTasks = { navController.navigate(Screen.Tasks.route) }
                            )
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                onLogout = {
                                    authViewModel.logout()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Tasks.route) {
                            TasksScreen(viewModel = taskViewModel)
                        }
                        composable(Screen.Breathing.route) {
                            BreathingScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Grounding.route) {
                            GroundingScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.MoodCheckIn.route) {
                            MoodCheckInScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.SemesterDetail.route) { backStackEntry ->
                            val semesterId = backStackEntry.arguments?.getString("semesterId") ?: "sem1"
                            SemesterDetailScreen(
                                semesterId = semesterId,
                                viewModel = academicViewModel,
                                onSubjectClick = { subjectId ->
                                    navController.navigate(Screen.SubjectDetail.createRoute(subjectId))
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.SubjectDetail.route) { backStackEntry ->
                            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: "os"
                            SubjectDetailScreen(
                                subjectId = subjectId,
                                viewModel = academicViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
