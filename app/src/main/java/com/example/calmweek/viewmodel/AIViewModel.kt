package com.example.calmweek.viewmodel

import com.example.calmweek.BuildConfig
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class Habit(
    val id: String,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean
)

data class GratitudeEntry(
    val id: String,
    val text: String,
    val date: String
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean
)

class AIViewModel : ViewModel() {
    var apiKey: String = BuildConfig.GEMINI_API_KEY

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    private val _messages = mutableStateListOf(
        ChatMessage("Hello! I am your AI Study Dost. Ask me any doubts about Operating Systems, DSA, DBMS, or need help planning your day?", false)
    )
    val messages: List<ChatMessage> = _messages

    private val _habits = mutableStateListOf(
        Habit("1", "Drank 8 Glasses of Water", "Stay hydrated while studying", false),
        Habit("2", "Revised 1 DSA Question", "Keep your problem-solving streak alive", false),
        Habit("3", "30 Mins Physical Exercise", "Keep your body energized", false),
        Habit("4", "Slept 7+ Hours", "Essential for memory consolidation", false)
    )
    val habits: List<Habit> = _habits

    private val _gratitudeEntries = mutableStateListOf(
        GratitudeEntry("1", "Grateful for a quiet morning and good coffee ☕", "Today"),
        GratitudeEntry("2", "Understood CPU scheduling algorithms clearly 🚀", "Yesterday")
    )
    val gratitudeEntries: List<GratitudeEntry> = _gratitudeEntries

    private val _badges = mutableStateListOf(
        Badge("1", "Algorithm Master", "Completed 10 DSA topics", "🏅", true),
        Badge("2", "Pomodoro Ninja", "Finished 20 focus sessions", "⏱️", true),
        Badge("3", "Zen Master", "Completed 5 calm breathing sessions", "🌿", false),
        Badge("4", "Streak Champion", "Maintained a 7-day study streak", "🔥", true)
    )
    val badges: List<Badge> = _badges

    private val _studyStreak = MutableStateFlow(7)
    val studyStreak: StateFlow<Int> = _studyStreak.asStateFlow()

    private val _aiSchedule = MutableStateFlow<String?>(null)
    val aiSchedule: StateFlow<String?> = _aiSchedule.asStateFlow()

    init {
        loadHabitsAndStreakFromFirebase()
    }

    private fun loadHabitsAndStreakFromFirebase() {
        val uid = auth.currentUser?.uid ?: return
        database.child("users").child(uid).child("productivity").get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val streakVal = snapshot.child("streak").getValue(Int::class.java)
                if (streakVal != null) {
                    _studyStreak.value = streakVal
                }
                val habitsSnap = snapshot.child("habits")
                for (habitSnap in habitsSnap.children) {
                    val id = habitSnap.key ?: continue
                    val completed = habitSnap.getValue(Boolean::class.java) ?: false
                    val index = _habits.indexOfFirst { it.id == id }
                    if (index != -1) {
                        _habits[index] = _habits[index].copy(isCompleted = completed)
                    }
                }
            }
        }
    }

    fun toggleHabit(habitId: String) {
        val index = _habits.indexOfFirst { it.id == habitId }
        if (index != -1) {
            val habit = _habits[index]
            val newCompleted = !habit.isCompleted
            _habits[index] = habit.copy(isCompleted = newCompleted)

            val uid = auth.currentUser?.uid
            if (uid != null) {
                database.child("users").child(uid).child("productivity").child("habits").child(habitId).setValue(newCompleted)
                if (newCompleted) {
                    val currentStreak = _studyStreak.value + 1
                    _studyStreak.value = currentStreak
                    database.child("users").child(uid).child("productivity").child("streak").setValue(currentStreak)
                }
            }
        }
    }

    fun addGratitudeEntry(text: String) {
        if (text.isNotBlank()) {
            val entry = GratitudeEntry(System.currentTimeMillis().toString(), text, "Today")
            _gratitudeEntries.add(0, entry)
            val uid = auth.currentUser?.uid
            if (uid != null) {
                database.child("users").child(uid).child("gratitude").child(entry.id).setValue(entry)
            }
        }
    }

    suspend fun sendMessage(prompt: String) {
        if (prompt.isBlank()) return
        _messages.add(ChatMessage(prompt, true))

        val reply = withContext(Dispatchers.IO) {
            callGeminiAPI(prompt)
        }
        _messages.add(ChatMessage(reply, false))
    }

    suspend fun generateStudySchedule(subjects: String, hours: String) {
        val prompt = "Create an optimized daily study timetable for a college student studying: $subjects for $hours hours today. Format clearly with time blocks."
        val schedule = withContext(Dispatchers.IO) {
            callGeminiAPI(prompt)
        }
        _aiSchedule.value = schedule
    }

    private fun callGeminiAPI(userPrompt: String): String {
        val currentKey = apiKey.ifBlank { BuildConfig.GEMINI_API_KEY }
        if (currentKey.isBlank()) {
            return "AI Key Missing: Please set GEMINI_API_KEY in local.properties or environment variables."
        }

        // Prioritizing gemini-2.5-flash and gemini-1.5-flash with fallback models
        val models = listOf("gemini-2.5-flash", "gemini-1.5-flash", "gemini-1.5-pro")
        var lastError = ""

        for (model in models) {
            try {
                val urlString = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$currentKey"
                val url = URL(urlString)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    setRequestProperty("Content-Type", "application/json")
                    doOutput = true
                    connectTimeout = 8000
                    readTimeout = 8000
                }

                val requestBody = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("text", "You are a friendly AI Study Dost helping an engineering student. Keep answers helpful, encouraging, and concise. $userPrompt")
                                })
                            })
                        })
                    })
                }

                connection.outputStream.use { os ->
                    val input = requestBody.toString().toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val responseString = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(responseString)
                    val candidates = jsonResponse.getJSONArray("candidates")
                    if (candidates.length() > 0) {
                        val candidate = candidates.getJSONObject(0)
                        val content = candidate.getJSONObject("content")
                        val parts = content.getJSONArray("parts")
                        if (parts.length() > 0) {
                            return parts.getJSONObject(0).getString("text")
                        }
                    }
                } else {
                    lastError = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "HTTP $responseCode"
                }
            } catch (e: Exception) {
                lastError = e.localizedMessage ?: "Unknown error"
            }
        }
        return "AI Connection Error: Unable to reach AI Study Dost ($lastError)"
    }
}
