package com.example.zenmind_ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zenmind_ai.data.model.SessionConfig
import com.example.zenmind_ai.ui.components.ZenButton
import com.example.zenmind_ai.ui.components.ZenCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionBuilderScreen(
    config: SessionConfig,
    onConfigChanged: (SessionConfig) -> Unit,
    onGenerate: () -> Unit,
    onBack: () -> Unit
) {
    val themes = listOf("Deep Relaxation", "Forest Calm", "Ocean Waves", "Mountain Peace", "Starry Night", "Sunrise Energy")
    val voiceStyles = listOf("Calm", "Warm", "Gentle Whisper", "Soothing Guide")
    val relaxationTypes = listOf("Guided Visualization", "Body Scan", "Breath Focus", "Loving Kindness", "Progressive Relaxation")
    val ambienceOptions = listOf("Nature Sounds", "Rain", "Ocean", "Forest Birds", "Wind", "Silence")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Build Your Session") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Theme Selection
            ZenCard {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChipGroup(
                    options = themes,
                    selectedOption = config.theme,
                    onOptionSelected = { onConfigChanged(config.copy(theme = it)) }
                )
            }

            // Duration
            ZenCard {
                Text(
                    text = "Duration: ${config.duration} minutes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = config.duration.toFloat(),
                    onValueChange = { onConfigChanged(config.copy(duration = it.toInt())) },
                    valueRange = 1f..30f,
                    steps = 28,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1 min", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("30 min", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Voice Style
            ZenCard {
                Text(
                    text = "Voice Style",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChipGroup(
                    options = voiceStyles,
                    selectedOption = config.voiceStyle,
                    onOptionSelected = { onConfigChanged(config.copy(voiceStyle = it)) }
                )
            }

            // Relaxation Type
            ZenCard {
                Text(
                    text = "Relaxation Type",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChipGroup(
                    options = relaxationTypes,
                    selectedOption = config.relaxationType,
                    onOptionSelected = { onConfigChanged(config.copy(relaxationType = it)) }
                )
            }

            // Background Ambience
            ZenCard {
                Text(
                    text = "Background Ambience",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChipGroup(
                    options = ambienceOptions,
                    selectedOption = config.backgroundAmbience,
                    onOptionSelected = { onConfigChanged(config.copy(backgroundAmbience = it)) }
                )
            }

            ZenButton(
                text = "Generate Meditation",
                onClick = onGenerate
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ChipGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                label = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
