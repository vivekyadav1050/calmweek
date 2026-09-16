package com.example.calmweek.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmweek.data.AuthRepository
import com.example.calmweek.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val isLoggedIn: Boolean
        get() = repository.currentUser != null

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.login(email, pass)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun signup(email: String, pass: String, name: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.signup(email, pass, name)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Signup failed")
            }
        }
    }

    fun saveProfile(user: User, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.saveProfile(user)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Success
                onComplete(true)
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Failed to save profile")
                onComplete(false)
            }
        }
    }

    fun logout() {
        repository.logout()
    }
}
