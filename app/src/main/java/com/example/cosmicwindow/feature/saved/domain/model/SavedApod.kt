package com.example.cosmicwindow.feature.saved.domain.model


data class SavedApod(
    val date: String,
    val title: String,
    val explanation: String,
    val localImagePath: String,
    val saveDate: String,
    val alt: String,
    val credit: String,
    val copyright: String,
    val articleUrl: String,
    val videoUrl: String
)
