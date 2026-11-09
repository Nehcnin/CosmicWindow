package com.example.cosmicwindow.feature.detail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.detail.presentation.components.DetailExplanation
import com.example.cosmicwindow.feature.detail.presentation.components.DetailHeader
import com.example.cosmicwindow.feature.detail.presentation.components.DetailImage
import com.example.cosmicwindow.feature.detail.presentation.components.DetailShareButton
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailCopyrightCard
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailVideoCard
import org.koin.androidx.compose.koinViewModel

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

    LaunchedEffect(date) {
        viewModel.loadApod(date)
    }
    when {
        isLoading || (apod == null && error == null) -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = CosmicTheme.colors.primary
                )
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
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(top = CosmicSize.small),
                ) {
                    item {
                        DetailHeader(
                            onBack = onBack,
                            context = context,
                            url = loadedApod.articleUrl,
                        )
                    }

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
                            color = CosmicTheme.colors.whitePrimary
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
