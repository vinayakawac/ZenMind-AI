package com.example.zenmind_ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.zenmind_ai.navigation.NavGraph
import com.example.zenmind_ai.ui.theme.ZenMindTheme
import com.example.zenmind_ai.viewmodel.ZenMindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ZenMindViewModel = hiltViewModel()
            val preferences by viewModel.userPreferences.collectAsState()

            ZenMindTheme(darkTheme = preferences.isDarkMode) {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}