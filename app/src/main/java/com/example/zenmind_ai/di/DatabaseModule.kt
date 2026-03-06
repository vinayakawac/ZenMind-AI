package com.example.zenmind_ai.di

import android.content.Context
import androidx.room.Room
import com.example.zenmind_ai.data.database.MeditationDao
import com.example.zenmind_ai.data.database.MeditationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MeditationDatabase {
        return Room.databaseBuilder(
            context,
            MeditationDatabase::class.java,
            "zenmind_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMeditationDao(database: MeditationDatabase): MeditationDao {
        return database.meditationDao()
    }
}
