package com.example.cosmicwindow.core.di

import com.example.cosmicwindow.BuildConfig
import com.example.cosmicwindow.core.network.RetrofitInstance
import com.example.cosmicwindow.feature.today.data.remote.NasaApiService
import com.example.cosmicwindow.feature.today.data.repository.ApodRepositoryImpl
import com.example.cosmicwindow.feature.today.domain.ApodRepository
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val todayModule = module {
    single<NasaApiService> {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val url = original.url.newBuilder()
                    .addQueryParameter(
                        "api_key",
                        BuildConfig.NASA_API_KEY
                    )
                    .build()

                val request = original.newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .build()

        RetrofitInstance.create(
            baseUrl = "https://science.nasa.gov/wp-json/wp/v2/",
            client = client,
            json = get<Json>()
        ).create(NasaApiService::class.java)
    }

    single<ApodRepository> {
        ApodRepositoryImpl(get())
    }

    viewModel {
        TodayViewModel(get())
    }
}