package com.example.zenmind_ai.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class UserPreferences(
    val voiceSpeed: Float = 1.0f,
    val voicePitch: Float = 1.0f,
    val defaultDuration: Int = 5,
    val isDarkMode: Boolean = true
)

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "zen_mind_preferences")

@Singleton
class UserPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val VOICE_SPEED = floatPreferencesKey("voice_speed")
        val VOICE_PITCH = floatPreferencesKey("voice_pitch")
        val DEFAULT_DURATION = intPreferencesKey("default_duration")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
        UserPreferences(
            voiceSpeed = prefs[Keys.VOICE_SPEED] ?: 1.0f,
            voicePitch = prefs[Keys.VOICE_PITCH] ?: 1.0f,
            defaultDuration = prefs[Keys.DEFAULT_DURATION] ?: 5,
            isDarkMode = prefs[Keys.IS_DARK_MODE] ?: true
        )
    }

    suspend fun updateVoiceSpeed(speed: Float) {
        context.dataStore.edit { it[Keys.VOICE_SPEED] = speed }
    }

    suspend fun updateVoicePitch(pitch: Float) {
        context.dataStore.edit { it[Keys.VOICE_PITCH] = pitch }
    }

    suspend fun updateDefaultDuration(duration: Int) {
        context.dataStore.edit { it[Keys.DEFAULT_DURATION] = duration }
    }

    suspend fun updateDarkMode(isDark: Boolean) {
        context.dataStore.edit { it[Keys.IS_DARK_MODE] = isDark }
    }
}
