package com.example.cosmicwindow.core.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.example.cosmicwindow.R

val SpaceGrotesk =
    FontFamily(
        Font(
            R.font.spacegrotesk_variable,
            weight = FontWeight.Normal,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(400),
                ),
        ),
        Font(
            R.font.spacegrotesk_variable,
            weight = FontWeight.Medium,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(500),
                ),
        ),
        Font(
            R.font.spacegrotesk_variable,
            weight = FontWeight.SemiBold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(600),
                ),
        ),
        Font(
            R.font.spacegrotesk_variable,
            weight = FontWeight.Bold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(700),
                ),
        ),
    )

val Inter =
    FontFamily(
        Font(
            R.font.inter_variable,
            weight = FontWeight.Normal,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(400),
                ),
        ),
        Font(
            R.font.inter_variable,
            weight = FontWeight.Medium,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(500),
                ),
        ),
        Font(
            R.font.inter_variable,
            weight = FontWeight.SemiBold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(600),
                ),
        ),
        Font(
            R.font.inter_variable,
            weight = FontWeight.Bold,
            variationSettings =
                FontVariation.Settings(
                    FontVariation.weight(700),
                ),
        ),
    )

val ABeeZee =
    FontFamily(
        Font(R.font.abeezee_regular),
    )

val LiberationMono =
    FontFamily(
        Font(R.font.liberationmono_regular),
    )
