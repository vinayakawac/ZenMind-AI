package com.example.zenmind_ai.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationDao {

    @Query("SELECT * FROM meditations ORDER BY timestamp DESC")
    fun getAllMeditations(): Flow<List<MeditationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeditation(meditation: MeditationEntity)

    @Delete
    suspend fun deleteMeditation(meditation: MeditationEntity)

    @Query("SELECT * FROM meditations WHERE id = :id")
    suspend fun getMeditationById(id: Long): MeditationEntity?
}
