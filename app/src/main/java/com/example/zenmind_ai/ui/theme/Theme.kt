package com.example.zenmind_ai.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val ZenDarkColorScheme = darkColorScheme(
    primary = MutedTeal,
    onPrimary = DeepSlate,
    primaryContainer = MutedTealDark,
    onPrimaryContainer = MutedTealLight,
    secondary = SoftIndigo,
    onSecondary = SoftWhite,
    secondaryContainer = DeepSlateLight,
    onSecondaryContainer = SoftIndigoLight,
    tertiary = SoftLavender,
    onTertiary = DeepSlate,
    background = NightSky,
    onBackground = SoftWhite,
    surface = DeepSlate,
    onSurface = SoftWhite,
    surfaceVariant = DeepSlateLight,
    onSurfaceVariant = WarmSand,
    error = ErrorDark,
    onError = DeepSlate
)

private val ZenLightColorScheme = lightColorScheme(
    primary = MutedTealDark,
    onPrimary = SoftWhite,
    primaryContainer = MutedTealLight,
    onPrimaryContainer = DeepSlate,
    secondary = SoftIndigo,
    onSecondary = SoftWhite,
    secondaryContainer = WarmSandLight,
    onSecondaryContainer = DeepSlateLight,
    tertiary = SoftLavender,
    onTertiary = DeepSlate,
    background = SoftWhite,
    onBackground = DeepSlate,
    surface = SoftWhite,
    onSurface = DeepSlate,
    surfaceVariant = WarmSandLight,
    onSurfaceVariant = DeepSlateLight,
    error = ErrorLight,
    onError = SoftWhite
)

private val ZenShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun ZenMindTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ZenDarkColorScheme else ZenLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ZenTypography,
        shapes = ZenShapes,
        content = content
    )
}