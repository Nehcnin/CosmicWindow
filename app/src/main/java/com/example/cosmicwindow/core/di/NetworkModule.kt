package com.example.cosmicwindow.core.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        Json{
            ignoreUnknownKeys = true
        }
    }


}