package com.example.cosmicwindow.feature.saved.presentation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.components.CosmicHeader
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.saved.presentation.components.OfflineSizeCard
import java.io.File
import kotlin.math.roundToInt
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.example.cosmicwindow.feature.saved.presentation.components.SavedApodCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    viewModel: SavedViewModel,
    onReadStory: (String, Boolean) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val savedApods by viewModel.savedApods.collectAsStateWithLifecycle()
    val storageSizeMB by viewModel.storageSizeMB.collectAsStateWithLifecycle()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current

    LaunchedEffect(scrollBehaviour, density) {
        scrollBehaviour.state.heightOffsetLimit =
            with(density) { -(CosmicTheme.sizes.largeXL.toPx()) }
    }

    LaunchedEffect(Unit) {
        viewModel.refreshStorageSize()
    }

    val pendingDeletes by viewModel.pendingDeletes.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val undoLabel = stringResource(R.string.common_undo)
    val deleteMessage = stringResource(R.string.saved_delete_success)

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
                title = stringResource(R.string.saved_header_title),
                subtitle = stringResource(R.string.saved_header_subtitle),
                firstIcon = R.drawable.ic_header2,
                firstIconSize = CosmicSize.iconL,
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = CosmicTheme.sizes.medium),
            verticalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.medium)
        ) {
            item {
                OfflineSizeCard(
                    count = savedApods.size,
                    size = storageSizeMB
                )
            }

            items(
                items = savedApods.filter { it.date !in pendingDeletes },
                key = {it.date}
            ) { apod ->
                SavedApodCard(
                    apod = apod,
                    onDeleteSaved = {
                        viewModel.deleteSavedApod(apod)

                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = deleteMessage,
                                actionLabel = undoLabel,
                                duration = SnackbarDuration.Indefinite
                            )

                            if(result == SnackbarResult.ActionPerformed){
                                viewModel.undoDelete(apod.date)
                            }
                        }
                    },
                    onReadStory = onReadStory
                )
            }
        }
    }
}
