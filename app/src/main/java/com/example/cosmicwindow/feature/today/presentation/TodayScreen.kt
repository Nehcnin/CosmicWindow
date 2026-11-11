package com.example.cosmicwindow.feature.today.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicErrorCard
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.today.presentation.components.TodayDateComponent
import com.example.cosmicwindow.feature.today.presentation.components.TodayScrollableContent
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(viewModel: TodayViewModel = koinViewModel(), onReadStory: (String) -> Unit) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current
    val headerHeight = CosmicTheme.sizes.cardXS

    LaunchedEffect(scrollBehaviour, density, headerHeight) {
        scrollBehaviour.state.heightOffsetLimit =
            with(density) { -headerHeight.toPx() }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        with(density) {
                            (headerHeight.toPx() + scrollBehaviour.state.heightOffset)
                                .coerceAtLeast(0f)
                                .toDp()
                        },
                    ).clipToBounds(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .requiredHeight(headerHeight)
                        .offset {
                            IntOffset(
                                0,
                                scrollBehaviour.state.heightOffset.roundToInt() / 2,
                            )
                        },
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
                    modifier =
                        Modifier.requiredHeight(
                            CosmicTheme.sizes.largeXL,
                        ),
                )

                TodayDateComponent(viewModel.date)
            }
        }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            when {
                viewModel.isLoading -> {
                    LottieLoading(
                        Modifier.size(CosmicTheme.sizes.cardL),
                    )
                }

                viewModel.error != null -> {
                    CosmicErrorCard(
                        callback = { viewModel.loadToday() },
                    )
                }

                else -> {
                    TodayScrollableContent(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel,
                        onReadStory = onReadStory,
                    )
                }
            }
        }
    }
}
