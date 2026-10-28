package com.example.cosmicwindow.feature.today.domain

import com.example.cosmicwindow.feature.today.domain.model.Apod

interface ApodRepository {
    suspend fun getToday(): Apod

    suspend fun getByDate(date: String): Apod

    suspend fun getRange(startDate: String, endDate: String): List<Apod>

    suspend fun getRandom(count: Int): List<Apod>
}