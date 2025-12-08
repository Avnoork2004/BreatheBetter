package com.example.breathebetter.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "moods")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val mood: String, // e.g., "Happy", "Sad", "Okay", "Anxious"
    val note: String?,
    val timestamp: Long = System.currentTimeMillis()
)