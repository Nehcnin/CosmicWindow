package com.example.cosmicwindow

import android.app.Application
import com.example.cosmicwindow.core.di.DetailModule
import com.example.cosmicwindow.core.di.EarthModule
import com.example.cosmicwindow.core.di.NetworkModule
import com.example.cosmicwindow.core.di.TodayModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CosmicWindowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CosmicWindowApp)
            modules(
                NetworkModule,
                TodayModule,
                DetailModule,
                EarthModule,
            )
        }
    }
}
