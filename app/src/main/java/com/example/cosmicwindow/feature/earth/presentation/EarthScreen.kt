package com.example.cosmicwindow.feature.earth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.feature.earth.presentation.components.EarthImage
import com.example.cosmicwindow.feature.earth.presentation.components.EarthPlayCard
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter

@Composable
fun EarthScreen(date: String, viewModel: EarthViewModel) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(date) {
        viewModel.loadDate(date)
    }

    LaunchedEffect(viewModel.epics, isPlaying) {
        if (viewModel.epics.isNotEmpty() && isPlaying) {
            while (true) {
                delay(500)
                currentIndex = (currentIndex + 1) % viewModel.epics.size
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CosmicHeader(
            title = "Earth in Motion",
            subtitle = "DISCOVER EPIC",
            firstIcon = R.drawable.ic_header2,
            lastIcon = R.drawable.ic_header_today,
            firstIconSize = CosmicSize.iconL,
            lastIconSize = CosmicSize.largeL,
            modifierFirst = Modifier.padding(CosmicSize.medium),
        )
        when {
            viewModel.isLoading -> {
                CircularProgressIndicator()
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
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = CircleShape,
                                ).padding(horizontal = CosmicSize.medium, vertical = CosmicSize.smallL)
                                .align(Alignment.CenterEnd),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cloud),
                            tint = CosmicNavTextSelected,
                            contentDescription = "",
                            modifier =
                                Modifier
                                    .padding(end = CosmicSize.smallL)
                                    .size(CosmicSize.mediumS),
                        )

                        Text(
                            text = "EPIC Feed",
                            style = MaterialTheme.typography.labelSmall,
                            color = CosmicIconGray,
                        )
                    }
                }
                EarthImage(viewModel.epics[currentIndex].imageUrl)
                EarthPlayCard(
                    viewModel.epics[currentIndex].captureTime.format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"),
                    ) +
                        " UTC",
                    currentIndex,
                    viewModel.epics.size,
                    isPlaying = isPlaying,
                    onIndexChange = { currentIndex = it },
                    onPlayPause = { isPlaying = !isPlaying },
                )
            }
        }
    }
}
