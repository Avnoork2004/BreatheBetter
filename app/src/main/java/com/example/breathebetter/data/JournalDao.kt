package com.example.breathebetter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JournalDao {
    @Insert
    suspend fun insert(journal: JournalEntry): Long

    @Query("SELECT * FROM journals WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getForUser(userId: Long): List<JournalEntry>
}