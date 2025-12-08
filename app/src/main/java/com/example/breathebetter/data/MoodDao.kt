package com.example.breathebetter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoodDao {
    @Insert
    suspend fun insert(mood: MoodEntry): Long

    @Query("SELECT * FROM moods WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getForUser(userId: Long): List<MoodEntry>
}