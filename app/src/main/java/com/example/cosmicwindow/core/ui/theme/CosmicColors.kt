package com.example.cosmicwindow.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CosmicColors(
    val primary: Color = CosmicPrimary,
    val onPrimary: Color = CosmicOnPrimary,
    val surface: Color = CosmicSurface,
    val surfaceVariant: Color = CosmicSurfaceVariant,
    val background: Color = CosmicBackground,
    val bodyGray: Color = CosmicIconGray,
    val bodyGrayVariant: Color = CosmicButtonGray,
    val error: Color = CosmicError,
    val errorBackground: Color = CosmicErrorBackground,
    val errorIcon: Color = CosmicErrorIcon,
    val selected: Color = CosmicNavTextSelected,
    val whitePrimary: Color = CosmicTitleColor,
    val backgroundVariant: Color = CosmicCardGradient,
    val border: Color = CosmicSurfaceBorder,
    val yellowPrimary: Color = CosmicYellow,
    val yellowBackground: Color = CosmicOnYellow,
    val backgroundDark: Color = CosmicBackgroundDark,
    val borderVariant: Color = CosmicTextFieldBorder
)

internal val LocalCosmicColors =
    staticCompositionLocalOf { CosmicColors() }
