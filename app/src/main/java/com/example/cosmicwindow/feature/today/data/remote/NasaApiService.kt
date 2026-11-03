package com.example.cosmicwindow.feature.today.data.remote

import com.example.cosmicwindow.feature.today.data.dto.ApodDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("apod-basic/")
    suspend fun getToday(): List<ApodDto>

    @GET("apod-basic/")
    suspend fun getByDate(
        @Query("date") date: String
    ): List<ApodDto>

    @GET("apod-basic/")
    suspend fun getRange(
        @Query("date_from") startDate: String,
        @Query("date_to") endDate: String
    ): List<ApodDto>

    @GET("apod-basic/")
    suspend fun getRandom(
        @Query("count") count: Int
    ): List<ApodDto>
}