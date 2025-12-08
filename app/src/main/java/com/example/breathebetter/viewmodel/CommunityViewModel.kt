package com.example.breathebetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breathebetter.data.AppRepository
import com.example.breathebetter.data.CommunityMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommunityViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AppRepository(application.applicationContext)

    private val _messages = MutableStateFlow<List<CommunityMessage>>(emptyList())
    val messages: StateFlow<List<CommunityMessage>> = _messages

    fun postMessage(content: String) {
        viewModelScope.launch {
            repo.addMessage(CommunityMessage(content = content))
            loadMessages()
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            _messages.value = repo.getMessages()
        }
    }
}