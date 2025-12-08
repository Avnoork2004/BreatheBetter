package com.example.breathebetter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert
    suspend fun insert(message: CommunityMessage): Long

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    suspend fun getAll(): List<CommunityMessage>
}