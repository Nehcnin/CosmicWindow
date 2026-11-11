package com.example.cosmicwindow.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object CosmicTheme {
    val colors: CosmicColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCosmicColors.current

    val typography: CosmicTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalCosmicTypography.current

    val sizes: CosmicSize
        get() = CosmicSize
}

@Composable
fun CosmicWindowTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalCosmicColors provides CosmicColors(),
        LocalCosmicTypography provides CosmicTextStyles(),
    ) {
        MaterialTheme(
            content = content,
        )
    }
}
