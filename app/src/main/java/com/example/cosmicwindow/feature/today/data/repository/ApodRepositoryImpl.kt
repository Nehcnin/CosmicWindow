package com.example.cosmicwindow.feature.today.data.repository

import com.example.cosmicwindow.feature.today.data.dto.toDomain
import com.example.cosmicwindow.feature.today.data.remote.NasaApiService
import com.example.cosmicwindow.feature.today.domain.ApodRepository
import com.example.cosmicwindow.feature.today.domain.model.Apod

class ApodRepositoryImpl(
    private val api: NasaApiService
) : ApodRepository {
    override suspend fun getToday(): Apod {
        return api.getToday().first().toDomain()
    }

    override suspend fun getByDate(date: String): Apod {
        return api.getByDate(date).first().toDomain()
    }

    override suspend fun getRange(
        startDate: String,
        endDate: String
    ): List<Apod> {
        return api.getRange(startDate, endDate).map { it.toDomain() }
    }

    override suspend fun getRandom(count: Int): List<Apod> {
        return api.getRandom(count).map { it.toDomain() }
    }

}