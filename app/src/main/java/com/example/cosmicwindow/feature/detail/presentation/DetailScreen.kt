package com.example.cosmicwindow.feature.detail.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicButtonGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTitleColor
import com.example.cosmicwindow.feature.detail.presentation.components.DetailHeader
import com.example.cosmicwindow.feature.detail.presentation.components.DetailImage
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import com.example.cosmicwindow.feature.detail.presentation.components.DetailExplanation
import com.example.cosmicwindow.feature.detail.presentation.components.DetailShareButton
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailCopyrightCard
import com.example.cosmicwindow.feature.detail.presentation.components.cards.DetailVideoCard

@Composable
fun DetailScreen(
    date: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
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
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            apod?.let { loadedApod ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = CosmicSize.small)
                ) {
                    item {
                        DetailHeader(
                            onBack = onBack,
                            context = context,
                            url = loadedApod.articleUrl
                        )
                    }

                    item {
                        DetailImage(
                            url = loadedApod.imgUrl
                        )
                    }

                    item {
                        Text(
                            text = loadedApod.title,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(
                                horizontal = CosmicSize.medium,
                                vertical = CosmicSize.mediumS
                            )
                        )
                    }

                    if (loadedApod.videoUrl != null) {
                        item {
                            DetailVideoCard(
                                loadedApod = loadedApod,
                                context = context
                            )
                        }
                    }

                    item {
                        DetailExplanation(loadedApod.explanation)
                    }

                    item{
                        DetailCopyrightCard(
                            credit = loadedApod.credit?:"NASA",
                            copyright = "Public Domain",
                            url = loadedApod.articleUrl?:"",
                            context = context
                        )
                    }
                    if(loadedApod.articleUrl!=null) {
                        item {
                            DetailShareButton(context = context, url = loadedApod.articleUrl)
                        }
                    }
                    //commitnak xd
                }
            }
        }
    }
}