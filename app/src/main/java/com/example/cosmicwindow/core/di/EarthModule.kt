package com.example.cosmicwindow.core.di

import coil3.ImageLoader
import coil3.imageLoader
import coil3.request.ImageRequest
import com.example.cosmicwindow.BuildConfig
import com.example.cosmicwindow.core.network.EpicApiService
import com.example.cosmicwindow.core.network.RetrofitInstance
import com.example.cosmicwindow.feature.earth.data.repository.EpicRepositoryImpl
import com.example.cosmicwindow.feature.earth.domain.repository.EpicRepository
import com.example.cosmicwindow.feature.earth.presentation.EarthViewModel
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val EarthModule =
    module {
        viewModel {
            EarthViewModel(
                repository = get(),
                imageLoader = get(),
                createImageRequest = get(),
            )
        }

        single<EpicApiService> {
            val client =
                OkHttpClient
                    .Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()

                        val url =
                            original.url
                                .newBuilder()
                                .addQueryParameter(
                                    "api_key",
                                    BuildConfig.NASA_API_KEY,
                                ).build()

                        val request = original.newBuilder().url(url).build()

                        chain.proceed(request)
                    }.build()

            RetrofitInstance
                .create(
                    baseUrl = "https://api.nasa.gov/",
                    client = client,
                    json = get<Json>(),
                ).create(EpicApiService::class.java)
        }

        single<EpicRepository> {
            EpicRepositoryImpl(get())
        }

        single<ImageLoader> {
            androidContext().imageLoader
        }

        single<(String) -> ImageRequest> {
            val context = androidContext()

            val factory: (String) -> ImageRequest = { url ->
                ImageRequest
                    .Builder(context)
                    .data(url)
                    .build()
            }

            factory
        }
    }
