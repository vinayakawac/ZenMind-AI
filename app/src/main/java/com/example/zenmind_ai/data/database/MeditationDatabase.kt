package com.example.zenmind_ai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MeditationEntity::class], version = 1, exportSchema = false)
abstract class MeditationDatabase : RoomDatabase() {
    abstract fun meditationDao(): MeditationDao
}
