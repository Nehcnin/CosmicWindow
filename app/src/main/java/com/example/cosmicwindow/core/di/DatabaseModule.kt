package com.example.cosmicwindow.core.di

import androidx.room3.Room
import com.example.cosmicwindow.core.database.CosmicDatabase
import com.example.cosmicwindow.core.storage.ImageStorage
import com.example.cosmicwindow.feature.saved.data.SavedApodDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val databaseModule =
    module {
        single {
            Room
                .databaseBuilder<CosmicDatabase>(
                    context = androidContext(),
                    name = "cosmic_database",
                ).build()
        }

        single<SavedApodDao> {
            get<CosmicDatabase>().savedApodDao()
        }

        single {
            ImageStorage(androidContext())
        }
    }
