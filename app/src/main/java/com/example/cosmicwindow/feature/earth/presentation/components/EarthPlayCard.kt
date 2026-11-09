package com.example.cosmicwindow.feature.earth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.ABeeZee
import com.example.cosmicwindow.core.ui.theme.CosmicButtonGray
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicOnPrimary
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.core.ui.theme.CosmicTitleColor
import com.example.cosmicwindow.core.ui.theme.LiberationMono
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarthPlayCard(
    time: String,
    currentIndex: Int,
    maxIndex: Int,
    isPlaying: Boolean,
    onIndexChange: (Int) -> Unit,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(CosmicSize.medium)
                .background(
                    color = CosmicTheme.colors.surface,
                    shape = RoundedCornerShape(CosmicSize.radiusL),
                )
                .padding(CosmicSize.medium),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = CosmicSize.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "CAPTURE TIME",
                    style = CosmicTheme.typography.bodySmallMedium,
                    color = CosmicTheme.colors.bodyGray,
                )
                Text(
                    text = time,
                    fontFamily = ABeeZee,
                    fontSize = 15.sp,
                    lineHeight = 28.sp,
                    letterSpacing = -0.45.sp,
                    fontWeight = FontWeight.Normal,
                    color = CosmicTheme.colors.whitePrimary
                )
            }
            Row(
                modifier =
                    Modifier
                        .background(
                            color = CosmicTheme.colors.surfaceVariant,
                            shape = CircleShape,
                        )
                        .padding(horizontal = CosmicSize.medium, vertical = CosmicSize.smallL),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .padding(end = CosmicSize.smallL)
                            .size(CosmicSize.smallL)
                            .background(
                                color = CosmicTheme.colors.primary,
                                shape = CircleShape,
                            ),
                )

                Text(
                    text = "Frame ${currentIndex + 1} of $maxIndex",
                    fontFamily = LiberationMono,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.55.sp,
                    fontWeight = FontWeight.Medium,
                    color = CosmicTheme.colors.selected
                )
            }
        }

        Slider(
            value = currentIndex.toFloat(),
            onValueChange = { onIndexChange(it.roundToInt()) },
            valueRange = 0f..(maxIndex - 1).coerceAtLeast(1).toFloat(),
            steps = 0,
            enabled = maxIndex > 1,
            modifier = Modifier.fillMaxWidth(),
            thumb = {
                Box(
                    modifier =
                        Modifier
                            .size(CosmicSize.medium)
                            .background(
                                color = CosmicTheme.colors.primary,
                                shape = CircleShape,
                            ),
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    modifier = Modifier.height(CosmicSize.smallL),
                    colors =
                        SliderDefaults.colors(
                            activeTrackColor = CosmicTheme.colors.surfaceVariant,
                            inactiveTrackColor = CosmicTheme.colors.surfaceVariant,
                        ),
                    drawStopIndicator = null,
                    thumbTrackGapSize = 0.dp,
                )
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Frame 1",
                fontFamily = LiberationMono,
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicIconGray,
            )

            Text(
                text = "Sequence: 24h",
                fontFamily = LiberationMono,
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicIconGray.copy(alpha = 0.6f),
            )

            Text(
                text = "Frame $maxIndex",
                fontFamily = LiberationMono,
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicIconGray,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = CosmicSize.smallL),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(CosmicSize.large)
                    .background(
                        color = CosmicButtonGray,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(onClick = onPrevious)
            ) {
                Icon(
                    contentDescription = "",
                    painter = painterResource(R.drawable.ic_prev),
                    modifier = Modifier
                        .size(CosmicSize.mediumS)
                        .align(Alignment.Center),
                    tint = CosmicTitleColor
                )
            }

            Box(
                Modifier
                    .padding(horizontal = CosmicSize.mediumL)
                    .size(CosmicSize.largeL)
                    .background(
                        color = CosmicTheme.colors.primary,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(onClick = onPlayPause)
            ) {
                Icon(
                    contentDescription = "",
                    painter = painterResource(
                        if (!isPlaying)
                            R.drawable.ic_play_button
                        else
                            R.drawable.ic_pause
                    ),
                    modifier = Modifier
                        .size(CosmicSize.iconS)
                        .align(Alignment.Center),
                    tint = CosmicTheme.colors.onPrimary
                )
            }

            Box(
                Modifier
                    .size(CosmicSize.large)
                    .background(
                        color = CosmicButtonGray,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(onClick = onNext)
            ) {
                Icon(
                    contentDescription = "",
                    painter = painterResource(R.drawable.ic_next),
                    modifier = Modifier
                        .size(CosmicSize.mediumS)
                        .align(Alignment.Center),
                    tint = CosmicTitleColor
                )
            }
        }

    }
}
