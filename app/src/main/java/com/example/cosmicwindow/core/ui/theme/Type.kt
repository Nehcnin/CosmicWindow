package com.example.cosmicwindow.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cosmicwindow.R

val SpaceGrotesk = FontFamily(
    Font(R.font.space_grotesk_regular)
)
val Inter = FontFamily(
    Font(R.font.inter_regular)
)

val CosmicTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)