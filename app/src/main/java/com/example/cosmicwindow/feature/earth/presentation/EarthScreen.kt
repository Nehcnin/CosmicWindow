package com.example.cosmicwindow.feature.earth.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.core.content.ContextCompat
import coil3.ImageLoader
import coil3.request.SuccessResult
import coil3.toBitmap
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.components.LottieLoading
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.earth.presentation.components.EarthImage
import com.example.cosmicwindow.feature.earth.presentation.components.EarthInfoCard
import com.example.cosmicwindow.feature.earth.presentation.components.EarthPlayCard
import com.example.cosmicwindow.feature.earth.presentation.util.detectEarthRadius
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarthScreen(
    date: String,
    viewModel: EarthViewModel,
    imageLoader: ImageLoader
) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current
    val context = LocalContext.current

    var earthRadiusFraction by remember(date){
        mutableStateOf<Float?>(null)
    }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.loadLocation()
            }
        }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            viewModel.loadLocation()
            Log.d(
                "EPIC_LOCATION",
                "Coarse permission granted: $granted"
            )
        } else {
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    LaunchedEffect(scrollBehaviour, density) {
        scrollBehaviour.state.heightOffsetLimit =
            with(density) { -(CosmicTheme.sizes.largeXL.toPx()) }
    }

    LaunchedEffect(date) {
        viewModel.loadDate(date)
    }

    val firstImageUrl = viewModel.epics.firstOrNull()?.imageUrl

    LaunchedEffect(date, firstImageUrl) {
        if(firstImageUrl == null) return@LaunchedEffect

        earthRadiusFraction = null

        try{
            val result = imageLoader.execute(
                viewModel.imageRequest(firstImageUrl)
            )

            if(result is SuccessResult){
                val radius = withContext(Dispatchers.Default){
                    val bitmap = result.image.toBitmap()

                    val softwareBitmap =
                        if(bitmap.config == Bitmap.Config.HARDWARE){
                            bitmap.copy(
                                Bitmap.Config.ARGB_8888,
                                false
                            )
                        } else{
                            bitmap
                        }

                    if(softwareBitmap != null){
                        try {
                            detectEarthRadius(softwareBitmap)
                        } finally {
                            if(softwareBitmap != bitmap){
                                softwareBitmap.recycle()
                            }
                        }
                    } else{
                        null
                    }
                }

                earthRadiusFraction = radius

                Log.d(
                    "EPIC_EARTH",
                    "Earth radius fraction: $radius",
                )
            }
        } catch (e: CancellationException){
            throw e
        } catch (e: Exception){
            Log.e(
                "EPIC_EARTH",
                "Earth radius detection failed",
                e,
            )
        }
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
                            (CosmicTheme.sizes.largeXL.toPx() + scrollBehaviour.state.heightOffset)
                                .coerceAtLeast(0f)
                                .toDp()
                        },
                    )
                    .clipToBounds(),
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
                                        )
                                        .padding(horizontal = CosmicSize.medium, vertical = CosmicSize.smallL)
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
                        val epic = viewModel.epics[viewModel.currentIndex]

                        EarthImage(
                            imageRequest = viewModel.imageRequest(epic.imageUrl),
                            imageLoader = imageLoader,
                            epic = epic,
                            userLocation = viewModel.userLocation,
                            earthRadiusFraction = earthRadiusFraction,
                        )
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
