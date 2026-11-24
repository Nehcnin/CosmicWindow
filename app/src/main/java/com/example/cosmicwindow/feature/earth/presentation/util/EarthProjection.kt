
package com.example.cosmicwindow.feature.earth.presentation.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun projectLocation(
    latitude: Double,
    longitude: Double,
    centerLatitude: Double,
    centerLongitude: Double
): Offset? {

    val lat = Math.toRadians(latitude)
    val centerLat = Math.toRadians(centerLatitude)
    val deltaLon = Math.toRadians(longitude - centerLongitude)

    val visibility =
        sin(centerLat) * sin(lat) +
            cos(centerLat) * cos(lat) * cos(deltaLon)

    if (visibility < 0.0) return null

    val x = cos(lat) * sin(deltaLon)
    val y = cos(centerLat) * sin(lat) -
        sin(centerLat) * cos(lat) * cos(deltaLon)

    val radius = sqrt(x * x + y * y).coerceIn(0.0, 1.0)

    val correction = 0.3
    val correctedRadius = radius +
        correction * radius * radius * radius * (1.0 - radius)

    val scale = if (radius > 0.0) correctedRadius / radius else 1.0

    return Offset(
        x = (x * scale).toFloat(),
        y = (-y * scale).toFloat()
    )
}
