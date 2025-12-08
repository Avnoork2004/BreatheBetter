package com.example.breathebetter.data

import android.content.Context

class AppRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val userDao = db.userDao()
    private val moodDao = db.moodDao()
    private val journalDao = db.journalDao()
    private val messageDao = db.messageDao()

    // Users
    suspend fun registerUser(user: UserEntity): Long = userDao.insert(user)
    suspend fun findUserByEmail(email: String) = userDao.findByEmail(email)
    suspend fun findUserById(id: Long) = userDao.findById(id)

    // Mood
    suspend fun addMood(mood: MoodEntry) = moodDao.insert(mood)
    suspend fun getMoodsForUser(userId: Long) = moodDao.getForUser(userId)

    // Journal
    suspend fun addJournal(journal: JournalEntry) = journalDao.insert(journal)
    suspend fun getJournalsForUser(userId: Long) = journalDao.getForUser(userId)

    // Community
    suspend fun addMessage(message: CommunityMessage) = messageDao.insert(message)
    suspend fun getMessages() = messageDao.getAll()
}