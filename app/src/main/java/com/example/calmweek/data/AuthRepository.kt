package com.example.calmweek.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser
import com.example.calmweek.model.User
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    //priavte val latest= AcademicViewModel

    private val database = FirebaseDatabase.getInstance().reference

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun login(email: String, pass: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(mapFirebaseException(e))
        }
    }

    suspend fun signup(email: String, pass: String, name: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    userId = firebaseUser.uid,
                    name = name,
                    email = email
                )
                database.child("users").child(firebaseUser.uid).setValue(user).await()
            }
            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(mapFirebaseException(e))
        }
    }

    suspend fun saveProfile(user: User): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid ?: user.userId
            if (uid.isNotEmpty()) {
                database.child("users").child(uid).setValue(user).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    private fun mapFirebaseException(e: Exception): Exception {
        val msg = e.message ?: ""
        return when {
            msg.contains("EMAIL_EXISTS", true) || msg.contains("address is already in use", true) ->
                Exception("An account with this email already exists.")
            msg.contains("INVALID_EMAIL", true) ->
                Exception("Please enter a valid email address.")
            msg.contains("WEAK_PASSWORD", true) ->
                Exception("Password should be at least 6 characters.")
            msg.contains("USER_NOT_FOUND", true) || msg.contains("INVALID_LOGIN_CREDENTIALS", true) ->
                Exception("Invalid email or password.")
            else -> Exception(msg.ifEmpty { "Authentication failed. Please try again." })
        }
    }
}
