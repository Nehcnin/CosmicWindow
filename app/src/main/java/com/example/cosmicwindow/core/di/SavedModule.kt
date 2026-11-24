package com.example.cosmicwindow.core.di

import com.example.cosmicwindow.feature.saved.data.repository.SavedRepositoryImpl
import com.example.cosmicwindow.feature.saved.domain.repository.SavedRepository
import com.example.cosmicwindow.feature.saved.presentation.SavedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val SavedModule =
    module {

        single<SavedRepository> {
            SavedRepositoryImpl(
                dao = get(),
                imageStorage = get(),
            )
        }

        viewModel {
            SavedViewModel(
                savedRepository = get(),
                imageStorage = get(),
            )
        }
    }
