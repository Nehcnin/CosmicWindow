package com.example.cosmicwindow.feature.today.presentation.components

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicPrimary
import com.example.cosmicwindow.core.ui.theme.CosmicSize

@OptIn(UnstableApi::class)
@Composable
fun TodayVideoPlayer(
    videoUrl: String,
    thumbnailUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var started by remember(videoUrl) {
        mutableStateOf(false)
    }

    val player = remember(context, videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            playWhenReady = false
        }
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CosmicSize.medium))
            .background(androidx.compose.ui.graphics.Color.Black),
        contentAlignment = Alignment.Center
    ) {

        if (started) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = player

                        useController = true
                        controllerShowTimeoutMs = 2000
                        controllerAutoShow = false
                        controllerHideOnTouch = true

                        // Kék Play/Pause ikon
                        val blueTint = ColorStateList.valueOf(
                            CosmicPrimary.toArgb()
                        )

                        findViewById<ImageView>(
                            androidx.media3.ui.R.id.exo_play_pause
                        )?.imageTintList = blueTint

                        findViewById<ImageView>(
                            androidx.media3.ui.R.id.exo_play
                        )?.imageTintList = blueTint

                        findViewById<ImageView>(
                            androidx.media3.ui.R.id.exo_pause
                        )?.imageTintList = blueTint
                    }
                },
                update = { view ->
                    if (view.player !== player) {
                        view.player = player
                    }
                }
            )
        } else {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "Video thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    started = true
                    player.prepare()
                    player.play()
                },
                modifier = Modifier
                    .size(CosmicSize.largeXL)
                    .clip(CircleShape)
                    .background(CosmicPrimary)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Play video",
                    modifier = Modifier.size(CosmicSize.iconM),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}