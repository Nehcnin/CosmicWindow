package com.example.cosmicwindow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
            var showSplash by remember {
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
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CosmicWindowTheme {
        Greeting("Android")
    }
}