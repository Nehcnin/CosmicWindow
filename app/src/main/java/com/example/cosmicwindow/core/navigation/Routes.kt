package com.example.cosmicwindow.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object TodayRoute

@Serializable
data class DetailRoute(
    val date: String,
    val isLocal: Boolean = false,
)

@Serializable
data class EarthRoute(
    val date: String,
)

@Serializable
data object ExploreRoute

@Serializable
data class ExploreDetailRoute(
    val id: String,
)

@Serializable
data object SavedRoute
