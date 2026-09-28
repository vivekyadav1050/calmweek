package com.example.calmweek.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSupportScreen(
    moodKey: String,
    onNavigateToCalm: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onBack: () -> Unit
) {
    val isStressed = moodKey == "Stressed" || moodKey == "Tired"
    val isGreat = moodKey == "Great" || moodKey == "Good"

    val emoji = when (moodKey) {
        "Great" -> "😊"
        "Good" -> "🙂"
        "Okay" -> "😐"
        "Stressed" -> "😟"
        "Tired" -> "😫"
        else -> "🌿"
    }

    val title = when {
        isStressed -> "We hear you. It's okay to feel overwhelmed."
        isGreat -> "Wonderful energy! Let's make the most of today."
        else -> "Every step forward counts."
    }

    var jokeText by remember { mutableStateOf("Fetching a fresh joke for you...") }
    var isLoadingJoke by remember { mutableStateOf(false) }

    fun fetchJoke() {
        isLoadingJoke = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://v2.jokeapi.dev/joke/Programming,Miscellaneous?safe-mode&type=single")
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 5000
                    readTimeout = 5000
                }
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val fetchedJoke = if (json.has("joke")) {
                    json.getString("joke")
                } else if (json.has("setup") && json.has("delivery")) {
                    json.getString("setup") + "\n\n" + json.getString("delivery")
                } else {
                    "Why do programmers prefer dark mode? Because light attracts bugs!"
                }
                withContext(Dispatchers.Main) {
                    jokeText = fetchedJoke
                    isLoadingJoke = false
                }
            } catch (_: Exception) {
                try {
                    val url2 = URL("https://official-joke-api.appspot.com/random_joke")
                    val conn2 = (url2.openConnection() as HttpURLConnection).apply {
                        connectTimeout = 5000
                        readTimeout = 5000
                    }
                    val resp2 = conn2.inputStream.bufferedReader().use { it.readText() }
                    val json2 = JSONObject(resp2)
                    val setup = json2.getString("setup")
                    val punchline = json2.getString("punchline")
                    withContext(Dispatchers.Main) {
                        jokeText = "$setup\n\n$punchline"
                        isLoadingJoke = false
                    }
                } catch (_: Exception) {
                    withContext(Dispatchers.Main) {
                        jokeText = "Why do programmers prefer dark mode? Because light attracts bugs! 🐛"
                        isLoadingJoke = false
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchJoke()
    }

    val baseTip = when {
        isStressed -> "🌿 Calm Tip: Close your eyes for 30 seconds, take 3 deep breaths, and let go of tension."
        isGreat -> "🚀 Study Tip: You're in the zone! Tackle your highest priority task right now while your momentum is high."
        else -> "☕ Tip: Drink a glass of water and focus on just one small task. You don't have to finish everything today."
    }

    val scale = remember { Animatable(0.5f) }
    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calm Support Companion") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .size(110.dp)
                        .scale(scale.value)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = emoji, style = MaterialTheme.typography.displayLarge)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Fresh Joke & Study Tip 🌿",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(
                                onClick = { fetchJoke() },
                                enabled = !isLoadingJoke
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "New Joke",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isLoadingJoke) "Loading fresh joke..." else "💡 $jokeText",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = baseTip,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isStressed) {
                    Button(
                        onClick = onNavigateToCalm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start 2-Min Breathing Exercise", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onNavigateToLearn,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Continue Studying 📚", fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Return to Dashboard")
                }
            }
        }
    }
}
