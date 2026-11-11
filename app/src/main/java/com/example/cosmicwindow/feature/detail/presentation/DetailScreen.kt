package com.example.cosmicwindow.feature.detail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.detail.presentation.components.DetailExplanation
import com.example.cosmicwindow.feature.detail.presentation.components.DetailHeader
import com.example.cosmicwindow.feature.detail.presentation.components.DetailImage
import com.example.cosmicwindow.feature.detail.presentation.components.DetailShareButton
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailCopyrightCard
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailVideoCard
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    date: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val apod by viewModel.apod.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val error by viewModel.error.collectAsState()

    val context = LocalContext.current

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current

    LaunchedEffect(scrollBehaviour, density) {
        scrollBehaviour.state.heightOffsetLimit =
            with(density) { -(CosmicTheme.sizes.large.toPx()) }
    }

    LaunchedEffect(date) {
        viewModel.loadApod(date)
    }

    when {
        isLoading || (apod == null && error == null) -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LottieLoading(Modifier.size(CosmicTheme.sizes.cardL))
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = error ?: "Unknown error",
                    color = CosmicTheme.colors.error,
                )
            }
        }

        else -> {
            apod?.let { loadedApod ->

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
                                        (CosmicTheme.sizes.large.toPx() + scrollBehaviour.state.heightOffset)
                                            .coerceAtLeast(0f)
                                            .toDp()
                                    },
                                ).clipToBounds(),
                    ) {
                        DetailHeader(
                            onBack = onBack,
                            context = context,
                            url = loadedApod.articleUrl,
                            modifier =
                                Modifier
                                    .requiredHeight(CosmicTheme.sizes.large)
                                    .offset {
                                        IntOffset(
                                            0,
                                            scrollBehaviour.state.heightOffset.roundToInt() / 2,
                                        )
                                    },
                        )
                    }

                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(top = CosmicSize.small),
                    ) {
                        item {
                            DetailImage(
                                url = loadedApod.imgUrl,
                            )
                        }

                        item {
                            Text(
                                text = loadedApod.title,
                                style = CosmicTheme.typography.titleLarge,
                                modifier =
                                    Modifier.padding(
                                        horizontal = CosmicSize.medium,
                                        vertical = CosmicSize.mediumS,
                                    ),
                                color = CosmicTheme.colors.whitePrimary,
                            )
                        }

                        if (loadedApod.videoUrl != null) {
                            item {
                                DetailVideoCard(
                                    loadedApod = loadedApod,
                                    context = context,
                                )
                            }
                        }

                        item {
                            DetailExplanation(loadedApod.explanation)
                        }

                        item {
                            DetailCopyrightCard(
                                credit = loadedApod.credit ?: "NASA",
                                copyright = "Public Domain",
                                url = loadedApod.articleUrl ?: "",
                                context = context,
                            )
                        }
                        if (loadedApod.articleUrl != null) {
                            item {
                                DetailShareButton(context = context, url = loadedApod.articleUrl)
                            }
                        }
                    }
                }
            }
        }
    }
}
