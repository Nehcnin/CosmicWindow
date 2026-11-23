package com.example.cosmicwindow.feature.earth.presentation.util

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

fun detectEarthRadius(bitmap: Bitmap): Float? {
    val width = bitmap.width
    val height = bitmap.height

    val centerX = width / 2f
    val centerY = height / 2f

    val maxRadius = min(width, height) / 2f
    val samples = 72

    var detectedRadius = 0f

    var radius = 0f

    while (radius < maxRadius) {
        var brightPixels = 0

        for (i in 0 until samples) {
            val angle = 2.0 * PI * i / samples

            val x = (
                centerX + cos(angle).toFloat() * radius
                ).roundToInt().coerceIn(0, width - 1)

            val y = (
                centerY + sin(angle).toFloat() * radius
                ).roundToInt().coerceIn(0, height - 1)

            val pixel = bitmap.getPixel(x, y)

            val brightness = maxOf(
                Color.red(pixel),
                Color.green(pixel),
                Color.blue(pixel),
            )

            if (brightness > 30) {
                brightPixels++
            }
        }

        val brightRatio = brightPixels.toFloat() / samples

        if (brightRatio >= 0.45f) {
            detectedRadius = radius
        }

        radius += 2f
    }

    if (detectedRadius <= 0f) return null

    return detectedRadius / maxRadius
}
