package com.example.cosmicwindow.core.di

import com.example.cosmicwindow.core.network.ExploreApiService
import com.example.cosmicwindow.core.network.RetrofitInstance
import com.example.cosmicwindow.feature.explore.data.repository.ExploreRepositoryImpl
import com.example.cosmicwindow.feature.explore.domain.ExploreRepository
import com.example.cosmicwindow.feature.explore.presentation.ExploreViewModel
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ExploreModule =
    module {
        viewModel{
            ExploreViewModel(
                repository = get()
            )
        }

        single<ExploreApiService>{
            val client = OkHttpClient.Builder().build()

            RetrofitInstance
                .create(
                    baseUrl = "https://images-api.nasa.gov/",
                    client = client,
                    json = get<Json>()
                ).create(ExploreApiService::class.java)
        }

        single<ExploreRepository>{
            ExploreRepositoryImpl(api = get())
        }
    }
