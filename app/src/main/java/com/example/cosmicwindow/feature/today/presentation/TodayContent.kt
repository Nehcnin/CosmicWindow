package com.example.cosmicwindow.feature.today.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cosmicwindow.feature.today.presentation.components.TodayHeader
import com.example.cosmicwindow.feature.today.presentation.components.TodayMainComponent

@Composable
fun TodayContent(
    modifier: Modifier = Modifier,
    viewModel: TodayViewModel
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TodayHeader()
        TodayMainComponent(viewModel)
    }
}