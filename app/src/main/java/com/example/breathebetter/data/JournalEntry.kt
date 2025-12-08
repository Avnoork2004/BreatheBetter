package com.example.breathebetter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journals")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val title: String?,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)