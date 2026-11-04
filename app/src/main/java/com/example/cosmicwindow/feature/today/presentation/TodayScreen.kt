package com.example.cosmicwindow.feature.today.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodayScreen(viewModel: TodayViewModel = koinViewModel(), onReadStory: (String) -> Unit) {
    TodayContent(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onReadStory = onReadStory,
    )
}
