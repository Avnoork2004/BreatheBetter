package com.example.breathebetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breathebetter.data.AppRepository
import com.example.breathebetter.data.JournalEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JournalViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AppRepository(application.applicationContext)

    private val _journals = MutableStateFlow<List<JournalEntry>>(emptyList())
    val journals: StateFlow<List<JournalEntry>> = _journals

    fun addJournal(userId: Long, title: String?, content: String) {
        viewModelScope.launch {
            repo.addJournal(JournalEntry(userId = userId, title = title, content = content))
            loadJournals(userId)
        }
    }

    fun loadJournals(userId: Long) {
        viewModelScope.launch {
            _journals.value = repo.getJournalsForUser(userId)
        }
    }
}