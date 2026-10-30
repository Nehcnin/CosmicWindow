package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayApodCard
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayCopyrightCard
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayVideoCard

@Composable
fun TodayScrollableContent(
    modifier: Modifier = Modifier,
    viewModel: TodayViewModel
){

    val apod: Apod? = viewModel.apod



    if(apod != null) {

        LazyColumn(
            modifier = modifier
                //.padding(horizontal = CosmicSize.medium)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CosmicSize.medium)
        ) {
            item {
                TodayApodCard(
                    apod,
                    modifier = Modifier
                        .padding(horizontal = CosmicSize.medium)
                )
            }
            item{
                TodayWeekRow(
                    viewModel
                )
            }
            if(apod.videoUrl != null) {
                item {
                    TodayVideoCard(
                        apod,
                        modifier = Modifier.padding(horizontal = CosmicSize.medium)
                    )
                }
            }
            item {
                TodayCopyrightCard(
                    credit = apod.credit ?: "NASA",
                    copyright = apod.copyright ?: "NASA",
                    modifier = Modifier.padding(horizontal = CosmicSize.medium)
                )
            }
        }
    }
}