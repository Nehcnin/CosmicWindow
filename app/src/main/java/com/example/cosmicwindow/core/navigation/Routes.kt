package com.example.cosmicwindow.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object TodayRoute

@Serializable
data class DetailRoute(
    val date: String,
)

@Serializable
data class EarthRoute(
    val date: String,
)
