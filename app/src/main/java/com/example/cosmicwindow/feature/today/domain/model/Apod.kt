package com.example.cosmicwindow.feature.today.domain.model

data class Apod(
    val date: String,
    val title: String,
    val explanation: String,
    val mediaType: String,
    val imgUrl: String?,
    val articleUrl: String?,
    val copyright: String?,
    val credit: String?,
    val alt: String?
)