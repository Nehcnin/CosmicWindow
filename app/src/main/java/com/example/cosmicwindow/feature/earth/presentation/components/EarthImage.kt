package com.example.cosmicwindow.feature.earth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected

// TABVIEW!!!!!!
@Composable
fun EarthImage(imageUrl: String) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Earth",
            modifier =
                Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
            contentScale = ContentScale.Fit,
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.95f)
                    .aspectRatio(1f)
                    .background(
                        brush =
                            Brush.radialGradient(
                                0.0f to Color.Transparent,
                                0.75f to Color.Transparent,
                                0.751f to MaterialTheme.colorScheme.background,
                                1f to MaterialTheme.colorScheme.background,
                            ),
                    ),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.95f)
                    .aspectRatio(1f)
                    .background(
                        brush =
                            Brush.radialGradient(
                                0.0f to Color.Transparent,
                                0.75f to Color.Transparent,
                                0.751f to CosmicNavTextSelected.copy(alpha = 0.2f),
                                1f to MaterialTheme.colorScheme.background,
                            ),
                    ),
        )
    }
}
