package com.example.cosmicwindow.feature.explore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun ExpandableDescription(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val minHeight = CosmicTheme.sizes.cardS

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        val maxHeight = this.maxHeight

        var expandedHeight by remember {
            mutableFloatStateOf(with(density) { minHeight.toPx() })
        }

        val maxHeightPx = with(density) { maxHeight.toPx() }
        val minHeightPx = with(density) { minHeight.toPx() }
        val scrollState = rememberScrollState()

        var contentHeight by remember {
            mutableIntStateOf(0)
        }

        val initialHeight =
            if (contentHeight == 0) {
                minOf(minHeightPx, maxHeightPx)
            } else {
                minOf(contentHeight.toFloat(), minHeightPx, maxHeightPx)
            }

        val allowedHeight =
            minOf(
                maxHeightPx,
                contentHeight.toFloat().coerceAtLeast(initialHeight),
            )
        Box {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            with(density) {
                                expandedHeight.coerceIn(initialHeight, allowedHeight).toDp()
                            } + CosmicTheme.sizes.large,
                        ).background(
                            brush =
                                Brush.verticalGradient(
                                    colorStops =
                                        arrayOf(
                                            0f to Color.Transparent,
                                            0.2f to CosmicTheme.colors.darkPrimary.copy(alpha = 0.8f),
                                            1f to CosmicTheme.colors.darkPrimary,
                                        ),
                                ),
                        ).align(Alignment.BottomCenter),
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = CosmicTheme.sizes.smallL)
                        .height(
                            with(density) {
                                expandedHeight
                                    .coerceIn(
                                        initialHeight,
                                        allowedHeight,
                                    ).toDp()
                            },
                        ).verticalScroll(scrollState)
                        .pointerInput(initialHeight, maxHeightPx) {
                            detectVerticalDragGestures { change, dragAmount ->
                                change.consume()

                                if (scrollState.value == 0 && dragAmount > 0 || expandedHeight < allowedHeight) {
                                    expandedHeight =
                                        (expandedHeight - dragAmount).coerceIn(initialHeight, allowedHeight)
                                } else {
                                    scrollState.dispatchRawDelta(-dragAmount)
                                }
                            }
                        }.align(Alignment.BottomCenter),
//                    .background(
//                        brush =
//                            Brush.verticalGradient(
//                                colorStops =
//                                    arrayOf(
//                                        0f to Color.Transparent,
//                                        0.2f to CosmicTheme.colors.darkPrimary.copy(alpha = 0.8f),
//                                        1f to CosmicTheme.colors.darkPrimary,
//                                    ),
//                            ),
//                    ),
            ) {
                Column(
                    modifier =
                        Modifier.onSizeChanged {
                            contentHeight = it.height
                        },
                ) {
                    Text(
                        text = title,
                        style = CosmicTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = CosmicTheme.colors.whitePrimary,
                        modifier = Modifier.padding(horizontal = CosmicTheme.sizes.medium),
                    )
                    Text(
                        text = description,
                        style = CosmicTheme.typography.bodySmall,
                        color = CosmicTheme.colors.bodyGray,
                        modifier =
                            Modifier
                                .padding(
                                    horizontal = CosmicTheme.sizes.medium,
                                    vertical = CosmicTheme.sizes.smallL,
                                ),
                    )
                }
            }
            if (scrollState.value < scrollState.maxValue) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(CosmicTheme.sizes.large)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.Transparent,
                                            CosmicTheme.colors.darkPrimary,
                                        ),
                                ),
                            ),
                )
            }
        }
    }
}
