package com.example.zenmind_ai.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditations")
data class MeditationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val theme: String,
    val duration: Int,
    val meditationText: String,
    val timestamp: Long = System.currentTimeMillis()
)
