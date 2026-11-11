package com.example.cosmicwindow.feature.today.data.repository

import com.example.cosmicwindow.core.network.NasaApiService
import com.example.cosmicwindow.feature.today.data.dto.toDomain
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.domain.repository.ApodRepository

class ApodRepositoryImpl(
    private val api: NasaApiService,
) : ApodRepository {
    override suspend fun getToday(): Apod = api.getToday().first().toDomain()

    override suspend fun getByDate(date: String): Apod = api.getByDate(date).toDomain()

    override suspend fun getRange(startDate: String, endDate: String): List<Apod> =
        api.getRange(startDate, endDate).map { it.toDomain() }

    override suspend fun getRandom(count: Int): List<Apod> = api.getRandom(count).map { it.toDomain() }
}
