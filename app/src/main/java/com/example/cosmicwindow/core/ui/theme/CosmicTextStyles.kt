package com.example.cosmicwindow.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CosmicTextStyles(
    val headerTitle: TextStyle =
        TextStyle(
            fontFamily = SpaceGrotesk,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-0.5f).sp,
        ),
    val headerBody: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (0.55).sp,
        ),
    val titleSmall: TextStyle =
        TextStyle(
            fontFamily = SpaceGrotesk,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp,
        ),
    val titleLarge: TextStyle =
        TextStyle(
            fontFamily = SpaceGrotesk,
            fontSize = 30.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.75).sp,
        ),
    val titleMedium: TextStyle =
        TextStyle(
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.6f).sp,
        ),
    val titleMediumS: TextStyle =
        TextStyle(
            fontFamily = SpaceGrotesk,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp,
        ),
    val bodyMedium: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 16.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Normal,
        ),
    val bodyMediumS: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
        ),
    val bodyMediumSemi: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.SemiBold,
        ),
    val bodySmallBold: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 12.sp,
            lineHeight = 26.sp,
            letterSpacing = (0.6f).sp,
            fontWeight = FontWeight.SemiBold,
        ),
    val bodySmall: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 12.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
        ),
    val bodySmallSBold: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 11.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp,
        ),
    val bodySmallMedium: TextStyle =
        TextStyle(
            fontFamily = Inter,
            fontSize = 11.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp,
        ),
)

internal val LocalCosmicTypography =
    staticCompositionLocalOf { CosmicTextStyles() }
