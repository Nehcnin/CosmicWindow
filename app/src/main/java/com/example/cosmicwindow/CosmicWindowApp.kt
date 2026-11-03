package com.example.cosmicwindow

import android.app.Application
import com.example.cosmicwindow.core.di.networkModule
import com.example.cosmicwindow.core.di.todayModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CosmicWindowApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CosmicWindowApp)
            modules(
                networkModule,
                todayModule
            )
        }
    }
}