package com.example.cosmicwindow.feature.detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    date: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {

    val apod by viewModel.apod.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val error by viewModel.error.collectAsState()


    LaunchedEffect(date) {
        viewModel.loadApod(date)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when{
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null ->{
                Text("Error $error")
            }
            apod != null -> {
                Text(apod!!.explanation)
            }
            else -> {
                CircularProgressIndicator()
            }

        }
    }
}