package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cosmicwindow.core.ui.components.CosmicErrorCard
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel

@Composable
fun TodayMainComponent(viewModel: TodayViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CosmicSize.mediumS)
    ) {
        TodayDateComponent(viewModel.date)
        if (viewModel.isLoading) {
            CircularProgressIndicator()

        } else {
            if (viewModel.error != null) {
                CosmicErrorCard(callback = {viewModel.loadToday()})
            }
            else{
                TodayScrollableContent(viewModel = viewModel)
            }
        }
    }
}