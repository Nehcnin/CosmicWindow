package com.example.cosmicwindow

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CosmicWindowApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CosmicWindowApp)
            modules()
        }
    }
}