package com.example.cosmicwindow.feature.earth.presentation.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun projectLocation(
    latitude: Double,
    longitude: Double,
    centerLatitude: Double,
    centerLongitude: Double
): Offset?{

    val lat = Math.toRadians(latitude)
    val centerLat = Math.toRadians(centerLatitude)
    val deltaLon = Math.toRadians(longitude - centerLongitude)

    val visibility =
        sin(centerLat) * sin(lat) +
            cos(centerLat) * cos(lat) * cos(deltaLon)

    if(visibility < 0.0) return null

    val x = cos(lat) * sin(deltaLon)
    val y = cos(centerLat) * sin(lat) -
        sin(centerLat) * cos(lat) * cos(deltaLon)

    return Offset(
        x = x.toFloat(),
        y = -y.toFloat()
    )

}
