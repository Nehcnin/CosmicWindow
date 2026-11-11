package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayApodCard
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayCopyrightCard
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayVideoCard

@Composable
fun TodayScrollableContent(
    modifier: Modifier = Modifier,
    viewModel: TodayViewModel,
    onReadStory: (String) -> Unit,
) {
    val apod: Apod? = viewModel.apod

    if (apod != null) {
        LazyColumn(
            modifier =
                modifier
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CosmicSize.medium),
        ) {
            item {
                TodayApodCard(
                    apod,
                    modifier =
                        Modifier
                            .padding(horizontal = CosmicSize.medium),
                    onReadStory = { onReadStory(apod.date) },
                )
            }
            item {
                TodayWeekRow(
                    viewModel,
                )
            }
            if (apod.videoUrl != null) {
                item {
                    TodayVideoCard(
                        apod,
                        modifier = Modifier.padding(horizontal = CosmicSize.medium),
                    )
                }
            }
            item {
                TodayCopyrightCard(
                    credit = apod.credit ?: "NASA",
                    copyright = apod.copyright ?: "NASA",
                    modifier = Modifier.padding(horizontal = CosmicSize.medium),
                )
            }

            item {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(CosmicTheme.sizes.medium)
                            .background(
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.Transparent,
                                            CosmicTheme.colors.backgroundDark,
                                        ),
                                ),
                            ),
                )
            }
        }
    }
}
