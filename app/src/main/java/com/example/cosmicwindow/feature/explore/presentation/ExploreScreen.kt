package com.example.cosmicwindow.feature.explore.presentation

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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.presentation.components.ExploreCard
import com.example.cosmicwindow.feature.explore.presentation.components.ExploreSearchBar
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(viewModel: ExploreViewModel) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current
    val gridState = rememberLazyStaggeredGridState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount

            val lastVisibleIndex = layoutInfo.visibleItemsInfo
                .maxOfOrNull { it.index } ?: -1

            totalItems > 0 && lastVisibleIndex >= totalItems - 8
        }
    }

    LaunchedEffect(shouldLoadMore, viewModel.items.size) {
        if (shouldLoadMore) {
            viewModel.loadNextPage()
        }
    }

    LaunchedEffect(scrollBehavior, density) {
        scrollBehavior.state.heightOffsetLimit =
            with(density) { -(CosmicTheme.sizes.largeXL.toPx()) }
    }

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            (CosmicTheme.sizes.cardL.toPx() + scrollBehavior.state.heightOffset)
                                .coerceAtLeast(0f)
                                .toDp()
                        },
                    )
                    .clipToBounds(),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CosmicHeader(
                    title = "Explore NASA",
                    subtitle = "IMAGE LIBRARY",
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
                                    scrollBehavior.state.heightOffset.roundToInt() / 2,
                                )
                            },
                )
                ExploreSearchBar(
                    query = viewModel.query,
                    onQueryChange = viewModel::onQueryChange,
                    onMicClick = {},
                    onSearch = viewModel::onSearch
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
                    text = viewModel.error ?: "Unknown error",
                )
            }

            viewModel.items.isEmpty() -> {
                Text("No EPIC images available for this date")
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(CosmicTheme.sizes.medium),
                    verticalItemSpacing = CosmicTheme.sizes.medium,
                    horizontalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.medium)
                ) {

                    itemsIndexed(viewModel.items) { index, item ->
                        ExploreCard(
                            item = item,
                            height = if (index % 2 == 1) 233.dp else 275.dp,
                        )
                    }

                    if (viewModel.isLoadingMore) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(CosmicTheme.sizes.large),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    color = CosmicTheme.colors.selected, modifier = Modifier.size(
                                        CosmicTheme.sizes.iconL
                                    )
                                )
                                Text(
                                    text = "Loading more NASA archive images...",
                                    style = CosmicTheme.typography.bodySmall,
                                    color = CosmicTheme.colors.bodyGray,
                                    modifier = Modifier.padding(start = CosmicTheme.sizes.smallL)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
