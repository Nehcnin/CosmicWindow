package com.example.cosmicwindow.core.network

import com.example.cosmicwindow.feature.earth.data.dto.EpicDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EpicApiService {
    @GET("EPIC/api/natural")
    suspend fun getLatest(): List<EpicDto>

    @GET("EPIC/api/natural/date/{date}")
    suspend fun getByDate(
        @Path("date") date: String,
    ): List<EpicDto>

    @GET("EPIC/api/enhanced")
    suspend fun getLatestEnhanced(): List<EpicDto>

    @GET("EPIC/api/enhanced/date/{date}")
    suspend fun getByDateEnhanced(
        @Path("date") date: String,
    ): List<EpicDto>
}
