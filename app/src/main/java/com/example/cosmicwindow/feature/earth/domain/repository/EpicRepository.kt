package com.example.cosmicwindow.feature.earth.domain.repository

import com.example.cosmicwindow.feature.earth.domain.model.Epic

interface EpicRepository {
    suspend fun getLatest(): List<Epic>

    suspend fun getByDate(date: String): List<Epic>

    suspend fun getLatestEnhanced(): List<Epic>

    suspend fun getByDateEnhanced(date: String): List<Epic>
}
