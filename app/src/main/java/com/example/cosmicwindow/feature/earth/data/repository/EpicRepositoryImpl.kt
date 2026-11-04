package com.example.cosmicwindow.feature.earth.data.repository

import com.example.cosmicwindow.core.network.EpicApiService
import com.example.cosmicwindow.feature.earth.data.dto.toDomain
import com.example.cosmicwindow.feature.earth.domain.model.Epic
import com.example.cosmicwindow.feature.earth.domain.repository.EpicRepository

class EpicRepositoryImpl(
    private val api: EpicApiService,
) : EpicRepository {
    override suspend fun getLatest(): List<Epic> = api.getLatest().map { it.toDomain() }

    override suspend fun getByDate(date: String): List<Epic> = api.getByDate(date).map { it.toDomain() }

    override suspend fun getLatestEnhanced(): List<Epic> =
        api.getLatestEnhanced().map { it.toDomain(type = "enhanced") }

    override suspend fun getByDateEnhanced(date: String): List<Epic> =
        api.getByDateEnhanced(date).map {
            it.toDomain(type = "enhanced")
        }
}
