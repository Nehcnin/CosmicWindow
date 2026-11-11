package com.example.cosmicwindow.feature.earth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.imageLoader
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.earth.presentation.components.EarthImage
import com.example.cosmicwindow.feature.earth.presentation.components.EarthInfoCard
import com.example.cosmicwindow.feature.earth.presentation.components.EarthPlayCard
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarthScreen(date: String, viewModel: EarthViewModel) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current

    LaunchedEffect(scrollBehaviour, density) {
        scrollBehaviour.state.heightOffsetLimit =
            with(density) { -(CosmicTheme.sizes.largeXL.toPx()) }
    }

    LaunchedEffect(date) {
        viewModel.loadDate(date)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            (64.dp.toPx() + scrollBehaviour.state.heightOffset)
                                .coerceAtLeast(0f)
                                .toDp()
                        },
                    ).clipToBounds(),
        ) {
            CosmicHeader(
                title = "Earth in Motion",
                subtitle = "DISCOVER EPIC",
                firstIcon = R.drawable.ic_header2,
                lastIcon = R.drawable.ic_header_today,
                firstIconSize = CosmicSize.iconL,
                lastIconSize = CosmicSize.largeL,
                modifierFirst = Modifier.padding(CosmicSize.medium),
                modifier =
                    Modifier
                        .requiredHeight(CosmicTheme.sizes.largeXL)
                        .offset {
                            IntOffset(
                                0,
                                scrollBehaviour.state.heightOffset.roundToInt() / 2,
                            )
                        },
            )
        }
        when {
            viewModel.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LottieLoading(Modifier.size(CosmicTheme.sizes.cardL))
                }
            }

            viewModel.error != null -> {
                Text(
                    text = viewModel.error ?: "Unknown error",
                )
            }

            viewModel.epics.isEmpty() -> {
                Text("No EPIC images available for this date")
            }

            else -> {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = CosmicSize.medium),
                        ) {
                            Row(
                                modifier =
                                    Modifier
                                        .background(
                                            color = CosmicTheme.colors.surface,
                                            shape = CircleShape,
                                        ).padding(horizontal = CosmicSize.medium, vertical = CosmicSize.smallL)
                                        .align(Alignment.CenterEnd),
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_cloud),
                                    tint = CosmicTheme.colors.selected,
                                    contentDescription = "",
                                    modifier =
                                        Modifier
                                            .padding(end = CosmicSize.smallL)
                                            .size(CosmicSize.mediumS),
                                )

                                Text(
                                    text = "EPIC Feed",
                                    style = CosmicTheme.typography.bodySmallMedium,
                                    color = CosmicTheme.colors.bodyGray,
                                )
                            }
                        }
                        EarthImage(viewModel.epics[viewModel.currentIndex].imageUrl)
                        EarthPlayCard(
                            viewModel.epics[viewModel.currentIndex].captureTime.format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                            ) +
                                " UTC",
                            viewModel.currentIndex,
                            viewModel.epics.size,
                            isPlaying = viewModel.isPlaying,
                            onIndexChange = viewModel::setFrame,
                            onPlayPause = viewModel::togglePlayback,
                            onPrevious = viewModel::previousFrame,
                            onNext = viewModel::nextFrame,
                        )

                        EarthInfoCard()
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
    }
}
