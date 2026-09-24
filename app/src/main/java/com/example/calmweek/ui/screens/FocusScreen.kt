package com.example.calmweek.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FocusScreen(
    onNavigateToTasks: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(25 * 60) }
    var isRunning by remember { mutableStateOf(false) }
    var isBreak by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning, timeLeft) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0) {
            isRunning = false
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Focus Session ⏱️",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isBreak) "Take a short break" else "Deep focus time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Pomodoro Circle Timer
            Surface(
                shape = RoundedCornerShape(150.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(240.dp),
                tonalElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(if (isRunning) "Pause" else "Start", style = MaterialTheme.typography.titleMedium)
                }

                OutlinedButton(
                    onClick = {
                        isRunning = false
                        timeLeft = if (isBreak) 5 * 60 else 25 * 60
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Reset")
                }
            }

            Button(
                onClick = onNavigateToTasks,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Today's Tasks", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
