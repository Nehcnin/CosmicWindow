package com.example.cosmicwindow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cosmicwindow.core.navigation.CosmicNavigation
import com.example.cosmicwindow.core.ui.theme.CosmicWindowTheme
import com.example.cosmicwindow.core.util.SplashScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val nativeSplashGone = mutableStateOf(false)

        splashScreen.setOnExitAnimationListener { provider ->
            provider.remove()
            nativeSplashGone.value = true
        }

        enableEdgeToEdge()

        setContent {
            var showSplash by rememberSaveable {
                mutableStateOf(true)
            }

            LaunchedEffect(nativeSplashGone.value) {
                if (nativeSplashGone.value) {
                    delay(500)
                    showSplash = false
                }
            }

            CosmicWindowTheme {
                if (showSplash) {
                    SplashScreen()
                } else {
                    CosmicNavigation()
                }
            }
        }
    }
}
