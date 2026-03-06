package com.example.zenmind_ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zenmind_ai.data.api.GeminiRepository
import com.example.zenmind_ai.data.database.MeditationDao
import com.example.zenmind_ai.data.database.MeditationEntity
import com.example.zenmind_ai.data.model.SessionConfig
import com.example.zenmind_ai.data.preferences.UserPreferences
import com.example.zenmind_ai.data.preferences.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZenMindViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val meditationDao: MeditationDao,
    private val preferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _sessionConfig = MutableStateFlow(SessionConfig())
    val sessionConfig: StateFlow<SessionConfig> = _sessionConfig.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _meditationText = MutableStateFlow("")
    val meditationText: StateFlow<String> = _meditationText.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val historyList: StateFlow<List<MeditationEntity>> = meditationDao.getAllMeditations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userPreferences: StateFlow<UserPreferences> = preferencesManager.userPreferencesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserPreferences())

    fun updateSessionConfig(config: SessionConfig) {
        _sessionConfig.value = config
    }

    fun generateMeditation() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            geminiRepository.generateMeditation(_sessionConfig.value)
                .onSuccess { text ->
                    _meditationText.value = text
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Failed to generate meditation"
                }

            _isLoading.value = false
        }
    }

    fun saveSession() {
        viewModelScope.launch {
            val config = _sessionConfig.value
            val text = _meditationText.value
            if (text.isNotBlank()) {
                meditationDao.insertMeditation(
                    MeditationEntity(
                        theme = config.theme,
                        duration = config.duration,
                        meditationText = text
                    )
                )
            }
        }
    }

    fun loadMeditation(entity: MeditationEntity) {
        _meditationText.value = entity.meditationText
        _sessionConfig.value = SessionConfig(
            theme = entity.theme,
            duration = entity.duration
        )
    }

    fun deleteMeditation(entity: MeditationEntity) {
        viewModelScope.launch {
            meditationDao.deleteMeditation(entity)
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun updateVoiceSpeed(speed: Float) {
        viewModelScope.launch { preferencesManager.updateVoiceSpeed(speed) }
    }

    fun updateVoicePitch(pitch: Float) {
        viewModelScope.launch { preferencesManager.updateVoicePitch(pitch) }
    }

    fun updateDefaultDuration(duration: Int) {
        viewModelScope.launch { preferencesManager.updateDefaultDuration(duration) }
    }

    fun updateDarkMode(isDark: Boolean) {
        viewModelScope.launch { preferencesManager.updateDarkMode(isDark) }
    }
}
