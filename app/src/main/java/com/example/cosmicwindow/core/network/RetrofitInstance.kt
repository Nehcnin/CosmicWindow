package com.example.cosmicwindow.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {
    fun create(
        baseUrl: String,
        client: OkHttpClient,
        json: Json
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "applicaion/json".toMediaType()
                )
            )
            .build()
    }
}