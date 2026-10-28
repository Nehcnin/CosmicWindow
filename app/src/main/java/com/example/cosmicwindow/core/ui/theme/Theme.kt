package com.example.cosmicwindow.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val CosmicDarkColorScheme = darkColorScheme(
    primary = CosmicPrimary,
    onPrimary = CosmicOnPrimary,

    secondary = CosmicSecondary,
    onSecondary = CosmicBackground,

    tertiary = CosmicTertiary,
    onTertiary = CosmicBackground,

    background = CosmicBackground,
    onBackground = CosmicOnBackground,

    surface = CosmicSurface,
    onSurface = CosmicOnBackground,

    surfaceVariant = CosmicSurfaceVariant,
    onSurfaceVariant = CosmicOnBackground,

    outline = CosmicOutline,
    error = CosmicError

)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CosmicWindowTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = CosmicDarkColorScheme,
        typography = CosmicTypography,
        content = content
    )
}