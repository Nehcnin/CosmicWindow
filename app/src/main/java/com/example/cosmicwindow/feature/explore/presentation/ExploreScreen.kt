package com.example.cosmicwindow.feature.explore.presentation

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import com.example.cosmicwindow.feature.explore.presentation.components.ExploreCard
import com.example.cosmicwindow.feature.explore.presentation.components.ExploreSearchBar
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(viewModel: ExploreViewModel, onItemClick: (ExploreItem) -> Unit) {
    val locale = LocalConfiguration.current.locales[0]
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current
    val gridState = rememberLazyStaggeredGridState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount

            val lastVisibleIndex =
                layoutInfo.visibleItemsInfo
                    .maxOfOrNull { it.index } ?: -1

            totalItems > 0 && lastVisibleIndex >= totalItems - 8
        }
    }

    val voiceLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val spokenText =
                    result.data
                        ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        ?.firstOrNull()

                spokenText?.let {
                    viewModel.onQueryChange(it)
                    viewModel.onSearch()
                }
            }
        }

    val voiceIntent =
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toLanguageTag())
            putExtra(RecognizerIntent.EXTRA_PROMPT, stringResource(R.string.explore_voice_prompt))
        }

    val headerHeight = (CosmicTheme.sizes.largeXL + CosmicTheme.sizes.cardS)

    LaunchedEffect(shouldLoadMore, viewModel.items.size) {
        if (shouldLoadMore) {
            viewModel.loadNextPage()
        }
    }

    LaunchedEffect(scrollBehavior, density) {
        scrollBehavior.state.heightOffsetLimit =
            with(density) { -(headerHeight.toPx()) }
    }

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            (headerHeight.toPx() + scrollBehavior.state.heightOffset)
                                .coerceAtLeast(0f)
                                .toDp()
                        },
                    ).clipToBounds(),
        ) {
            Column(
                modifier =
                    Modifier
                        .requiredHeight(headerHeight)
                        .fillMaxWidth()
                        .offset {
                            IntOffset(
                                0,
                                scrollBehavior.state.heightOffset.roundToInt() / 2,
                            )
                        },
            ) {
                CosmicHeader(
                    title = stringResource(R.string.explore_header_title),
                    subtitle = stringResource(R.string.explore_header_subtitle),
                    firstIcon = R.drawable.ic_header2,
                    lastIcon = R.drawable.ic_header_today,
                    firstIconSize = CosmicSize.iconL,
                    lastIconSize = CosmicSize.largeL,
                    modifierFirst = Modifier.padding(CosmicSize.medium),
                    modifier = Modifier.requiredHeight(CosmicTheme.sizes.largeXL),
                )
                ExploreSearchBar(
                    query = viewModel.query,
                    onQueryChange = viewModel::onQueryChange,
                    onMicClick = { voiceLauncher.launch(voiceIntent) },
                    onSearch = viewModel::onSearch,
                    onKeywordChange = viewModel::onKeywordChange,
                    keyword = viewModel.keywords,
                    modifier = Modifier.requiredHeight(CosmicTheme.sizes.cardS),
                )
            }
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
                    text = viewModel.error ?: stringResource(R.string.common_unknown_error),
                )
            }

            viewModel.items.isEmpty() -> {
                Text(
                    text = stringResource(R.string.earth_no_images),
                    style = CosmicTheme.typography.bodyMedium,
                    color = CosmicTheme.colors.whitePrimary,
                )
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    state = gridState,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                    contentPadding = PaddingValues(horizontal = CosmicTheme.sizes.medium),
                    verticalItemSpacing = CosmicTheme.sizes.medium,
                    horizontalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.medium),
                ) {
                    itemsIndexed(viewModel.items) { index, item ->
                        ExploreCard(
                            item = item,
                            onClick = onItemClick,
                            height = if (index % 2 == 1) 233.dp else 275.dp,
                        )
                    }

                    if (viewModel.isLoadingMore) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(CosmicTheme.sizes.large),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                CircularProgressIndicator(
                                    color = CosmicTheme.colors.selected,
                                    modifier =
                                        Modifier.size(
                                            CosmicTheme.sizes.iconL,
                                        ),
                                )
                                Text(
                                    text = stringResource(R.string.explore_loading_more),
                                    style = CosmicTheme.typography.bodySmall,
                                    color = CosmicTheme.colors.bodyGray,
                                    modifier = Modifier.padding(start = CosmicTheme.sizes.smallL),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
