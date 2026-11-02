package com.example.cosmicwindow.core.di

import com.example.cosmicwindow.feature.detail.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel {
        DetailViewModel(get())
    }
}