import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val env = Properties().apply {
    val envFile = rootProject.file(".env")
    if (envFile.exists()) {
        envFile.inputStream().use { load(it) }
    }
}

val nasaApiKey = env.getProperty("NASA_API_KEY")
    ?: error("NASA_API_KEY missing from .env")

android {
    namespace = "com.example.cosmicwindow"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.cosmicwindow"
        minSdk = 35
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "NASA_API_KEY",
            "\"$nasaApiKey\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx)

    // OkHttp
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}