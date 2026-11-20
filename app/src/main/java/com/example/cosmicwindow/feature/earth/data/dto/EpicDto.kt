package com.example.cosmicwindow.feature.earth.data.dto

import com.example.cosmicwindow.feature.earth.domain.model.Epic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class EpicDto(
    val image: String,
    val date: String,
    @SerialName("centroid_coordinates")
    val centroidCoordinates: EpicCoordinatesDto
)

@Serializable
data class EpicCoordinatesDto(
    val lat: Double,
    val lon: Double
)

fun EpicDto.toDomain(type: String = "natural"): Epic {
    val captureTime =
        LocalDateTime.parse(
            date,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
        )

    val datePath =
        captureTime.format(
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        )

    return Epic(
        imageUrl = "https://epic.gsfc.nasa.gov/archive/$type/$datePath/png/$image.png",
        captureTime = captureTime,
        centerLatitude = centroidCoordinates.lat,
        centerLongitude = centroidCoordinates.lon
    )
}
