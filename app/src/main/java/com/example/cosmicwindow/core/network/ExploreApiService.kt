package com.example.cosmicwindow.core.network

import com.example.cosmicwindow.feature.explore.data.dto.ExploreResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApiService {
    @GET("search")
    suspend fun getImages(
        @Query("q") query: String?=null,
        @Query("media_type") mediaType: String = "image",
        @Query("page_size") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): ExploreResponse
}
