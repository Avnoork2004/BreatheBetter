package com.example.breathebetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breathebetter.data.AppRepository
import com.example.breathebetter.data.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AppRepository(application.applicationContext)

    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId: StateFlow<Long?> = _currentUserId

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repo.findUserByEmail(email)
            if (user == null) {
                _authError.value = "User not found"
                return@launch
            }
            if (user.password != password) {
                _authError.value = "Invalid credentials"
                return@launch
            }
            _authError.value = null
            _currentUserId.value = user.id
        }
    }

    fun register(first: String, last: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (repo.findUserByEmail(email) != null) {
                _authError.value = "Email already used"
                return@launch
            }
            val id = repo.registerUser(UserEntity(firstName = first, lastName = last, email = email, password = password))
            _authError.value = null
            _currentUserId.value = id
            onSuccess()
        }
    }

    fun logout() {
        _currentUserId.value = null
    }
}