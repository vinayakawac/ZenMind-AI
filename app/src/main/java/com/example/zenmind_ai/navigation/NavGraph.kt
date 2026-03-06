package com.example.zenmind_ai.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zenmind_ai.ui.screens.HistoryScreen
import com.example.zenmind_ai.ui.screens.HomeScreen
import com.example.zenmind_ai.ui.screens.PlayerScreen
import com.example.zenmind_ai.ui.screens.SessionBuilderScreen
import com.example.zenmind_ai.ui.screens.SettingsScreen
import com.example.zenmind_ai.viewmodel.ZenMindViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object SessionBuilder : Screen("session_builder")
    data object Player : Screen("player")
    data object History : Screen("history")
    data object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ZenMindViewModel = hiltViewModel()
) {
    val sessionConfig by viewModel.sessionConfig.collectAsState()
    val meditationText by viewModel.meditationText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val historyList by viewModel.historyList.collectAsState()
    val preferences by viewModel.userPreferences.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartSession = { navController.navigate(Screen.SessionBuilder.route) },
                onHistoryClick = { navController.navigate(Screen.History.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.SessionBuilder.route) {
            SessionBuilderScreen(
                config = sessionConfig,
                onConfigChanged = { viewModel.updateSessionConfig(it) },
                onGenerate = {
                    viewModel.generateMeditation()
                    navController.navigate(Screen.Player.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Player.route) {
            PlayerScreen(
                meditationText = meditationText,
                isLoading = isLoading,
                error = error,
                preferences = preferences,
                onBack = { navController.popBackStack() },
                onSave = { viewModel.saveSession() },
                onClearError = { viewModel.clearError() }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                meditations = historyList,
                onMeditationClick = { meditation ->
                    viewModel.loadMeditation(meditation)
                    navController.navigate(Screen.Player.route)
                },
                onDeleteClick = { viewModel.deleteMeditation(it) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                preferences = preferences,
                onVoiceSpeedChange = { viewModel.updateVoiceSpeed(it) },
                onVoicePitchChange = { viewModel.updateVoicePitch(it) },
                onDefaultDurationChange = { viewModel.updateDefaultDuration(it) },
                onDarkModeChange = { viewModel.updateDarkMode(it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
