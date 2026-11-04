package com.example.cosmicwindow.feature.today.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.feature.today.presentation.components.TodayMainComponent

@Composable
fun TodayContent(
    modifier: Modifier = Modifier,
    viewModel: TodayViewModel,
    onReadStory: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CosmicHeader(
            title = "Cosmic Window",
            subtitle = "ASTRONOMY PICTURE OF THE DAY",
            firstIcon = R.drawable.ic_header_today,
            lastIcon = R.drawable.ic_header_calendar,
            showDatePicker = true,
            selectedDate = viewModel.date,
            onDateSelected = { date ->
                viewModel.updateDate(date)
            },
        )
        TodayMainComponent(viewModel, onReadStory = onReadStory)
    }
}
