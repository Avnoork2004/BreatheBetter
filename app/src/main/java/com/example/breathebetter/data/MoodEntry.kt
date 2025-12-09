package com.example.breathebetter.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "moods")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val mood: String, //"Happy", "Sad", "Okay", "Anxious" Emoji based
    val note: String?,
    val timestamp: Long = System.currentTimeMillis()
)