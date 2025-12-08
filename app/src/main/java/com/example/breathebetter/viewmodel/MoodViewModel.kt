package com.example.breathebetter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.breathebetter.data.AppRepository
import com.example.breathebetter.data.MoodEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AppRepository(application.applicationContext)

    private val _moods = MutableStateFlow<List<MoodEntry>>(emptyList())
    val moods: StateFlow<List<MoodEntry>> = _moods

    fun addMood(userId: Long, mood: String, note: String?) {
        viewModelScope.launch {
            repo.addMood(MoodEntry(userId = userId, mood = mood, note = note))
            loadMoods(userId)
        }
    }

    fun loadMoods(userId: Long) {
        viewModelScope.launch {
            _moods.value = repo.getMoodsForUser(userId)
        }
    }
}