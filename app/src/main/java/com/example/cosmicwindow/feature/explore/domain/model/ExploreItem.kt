package com.example.cosmicwindow.feature.explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ExploreItem(
    val center: String,
    val date: String,
    val description: String,
    val keywords: List<String>,
    val id: String,
    val title: String,
    val imageUrl: String,
)
