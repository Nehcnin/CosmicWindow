package com.example.cosmicwindow.feature.today.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cosmicwindow.core.ui.components.CosmicBottomBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodayScreen(
    viewModel: TodayViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { CosmicBottomBar() }
    ) { innerPadding ->
        TodayContent(
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel
        )
    }
}