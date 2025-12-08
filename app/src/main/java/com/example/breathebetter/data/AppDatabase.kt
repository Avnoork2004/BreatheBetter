package com.example.breathebetter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class, MoodEntry::class, JournalEntry::class, CommunityMessage::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moodDao(): MoodDao
    abstract fun journalDao(): JournalDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "breathebetter.db"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}