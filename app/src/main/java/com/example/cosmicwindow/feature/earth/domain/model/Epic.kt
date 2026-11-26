package com.example.cosmicwindow.feature.earth.domain.model

import java.time.LocalDateTime

data class Epic(
    val imageUrl: String,
    val captureTime: LocalDateTime,
    val centerLatitude: Double,
    val centerLongitude: Double,
)
