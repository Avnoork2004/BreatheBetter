package com.example.breathebetter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class CommunityMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
    // anonymous messages, no userId stored
)