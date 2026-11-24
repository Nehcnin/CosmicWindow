
package com.example.cosmicwindow.feature.earth.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.asPainter
import coil3.compose.LocalPlatformContext
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.location.GeoLocation
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.presentation.util.projectLocation

@Composable
fun EarthImage(
    image: coil3.Image?,
    epic: Epic,
    userLocation: GeoLocation?,
    earthRadiusFraction: Float?,
    locationEnabled: Boolean,
    onLocationEnabledChange: (Boolean)-> Unit
) {
    val radius = earthRadiusFraction ?: 0.75f
    val radiusScale = radius *1.1f
    val context = LocalPlatformContext.current

    val projected = remember(
        epic.centerLatitude,
        epic.centerLongitude,
        userLocation,
        locationEnabled
    ) {
        if(locationEnabled) {
            userLocation?.let { location ->
                projectLocation(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    centerLatitude = epic.centerLatitude,
                    centerLongitude = epic.centerLongitude,
                )
            }
        }else{
            null
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
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        if (image != null) {
            Image(
                painter = image.asPainter(context),
                contentDescription = stringResource(R.string.earth_image_description),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit,
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center,
            ) {
                LottieLoading()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .aspectRatio(1f)
                .background(
                    brush = Brush.radialGradient(
                        0.0f to Color.Transparent,
                        radius to Color.Transparent,
                        radius + 0.001f to CosmicTheme.colors.background,
                        1f to CosmicTheme.colors.background,
                    ),
                ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .aspectRatio(1f)
                .background(
                    brush = Brush.radialGradient(
                        0.0f to Color.Transparent,
                        radius to Color.Transparent,
                        radius + 0.001f to CosmicTheme.colors.selected.copy(alpha = 0.2f),
                        1f to CosmicTheme.colors.background,
                    ),
                ),
        )

        if (projected != null && earthRadiusFraction != null && image != null) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ){
                val earthRadius = maxWidth/2*earthRadiusFraction

                Icon(
                    painter = painterResource(R.drawable.ic_map_pin),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .offset(
                            x = earthRadius*projected.x,
                            y = earthRadius*projected.y- CosmicTheme.sizes.medium/2
                        )
                        .size(CosmicTheme.sizes.medium)
                )
            }
        }

        LocationPermissionButton(
            enabled = locationEnabled,
            onEnableChange = onLocationEnabledChange,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
