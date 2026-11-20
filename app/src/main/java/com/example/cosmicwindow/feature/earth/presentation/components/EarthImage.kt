package com.example.cosmicwindow.feature.earth.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.cosmicwindow.core.location.GeoLocation
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.presentation.util.projectLocation

@Composable
fun EarthImage(
    imageRequest: ImageRequest,
    imageLoader: ImageLoader,
    epic: Epic,
    userLocation: GeoLocation?,
    earthRadiusFraction: Float?,
)
{

    val radius = earthRadiusFraction?: 0.75f

    val projected = remember(
        epic.centerLatitude,
        epic.centerLongitude,
        userLocation,
    ) {
        userLocation?.let { location ->
            projectLocation(
                latitude = location.latitude,
                longitude = location.longitude,
                centerLatitude = epic.centerLatitude,
                centerLongitude = epic.centerLongitude,
            )
        }
    }

    LaunchedEffect(projected, earthRadiusFraction) {
        Log.d(
            "EPIC_GPS",
            "GPS: $userLocation, " +
                "Center: ${epic.centerLatitude}, ${epic.centerLongitude}, " +
                "Projected: $projected, " +
                "Radius: $earthRadiusFraction"
        )
    }


    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Earth",
            imageLoader = imageLoader,
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
                                radius to Color.Transparent,
                                radius+0.001f to CosmicTheme.colors.background,
                                1f to CosmicTheme.colors.background,
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
                                radius to Color.Transparent,
                                radius+0.001f to CosmicTheme.colors.selected.copy(alpha = 0.2f),
                                1f to CosmicTheme.colors.background,
                            ),
                    ),
        )

        if (projected != null && earthRadiusFraction != null) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
            ) {
                val earthRadius =
                    size.minDimension / 2f * earthRadiusFraction

                val point = Offset(
                    x = center.x + projected.x * earthRadius,
                    y = center.y + projected.y * earthRadius,
                )

                drawCircle(
                    color = Color.Red,
                    radius = 2.dp.toPx(),
                    center = point,
                )
            }
        }
    }
}
