package com.example.cosmicwindow.feature.saved.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "saved_apods")
data class SavedApodEntity(
    @PrimaryKey
    val date: String,
    val title: String,
    val explanation: String,
    val imageUrl: String,
    val localImagePath: String,
    val SavedDate: String,
    val alt: String,
    val credit: String,
    val copyright: String,
    val articleUrl: String,
    val videoUrl: String,
)
