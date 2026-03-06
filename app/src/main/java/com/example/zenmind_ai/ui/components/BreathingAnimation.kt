package com.example.zenmind_ai.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BreathingAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    // Total cycle: 4s inhale + 4s hold + 4s exhale = 12 seconds
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "breathProgress"
    )

    // Scale: inhale 0-0.33 -> 0.6 to 1.0, hold 0.33-0.66 -> 1.0, exhale 0.66-1.0 -> 1.0 to 0.6
    val scale = when {
        progress < 1f / 3f -> 0.6f + (progress / (1f / 3f)) * 0.4f    // Inhale
        progress < 2f / 3f -> 1.0f                                      // Hold
        else -> 1.0f - ((progress - 2f / 3f) / (1f / 3f)) * 0.4f      // Exhale
    }

    val breathLabel = when {
        progress < 1f / 3f -> "Inhale"
        progress < 2f / 3f -> "Hold"
        else -> "Exhale"
    }

    val outerAlpha = when {
        progress < 1f / 3f -> 0.2f + (progress / (1f / 3f)) * 0.3f
        progress < 2f / 3f -> 0.5f
        else -> 0.5f - ((progress - 2f / 3f) / (1f / 3f)) * 0.3f
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow circle
        Canvas(
            modifier = Modifier
                .size(size)
                .alpha(outerAlpha)
        ) {
            drawCircle(
                color = color.copy(alpha = 0.3f),
                radius = this.size.minDimension / 2 * scale * 1.2f
            )
        }

        // Main breathing circle
        Canvas(
            modifier = Modifier.size(size)
        ) {
            drawCircle(
                color = color.copy(alpha = 0.7f),
                radius = this.size.minDimension / 2 * scale
            )
        }

        // Inner circle
        Canvas(
            modifier = Modifier.size(size * 0.5f)
        ) {
            drawCircle(
                color = color.copy(alpha = 0.9f),
                radius = this.size.minDimension / 2 * scale
            )
        }

        Text(
            text = breathLabel,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
