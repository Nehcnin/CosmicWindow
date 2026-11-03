package com.example.cosmicwindow.feature.detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicCardGradient
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTitleColor

@Composable
fun DetailImage(
    url: String?
){

    var fullscreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(CosmicSize.cardXXXL)
    ) {
        AsyncImage(
            model = url,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.4f to Color.Transparent,
                            1f to CosmicCardGradient
                        )
                    )
                )
        )

        IconButton(
            onClick = {
                fullscreen = true
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(CosmicSize.medium)
                .size(CosmicSize.largeL)
                .background(
                    color = CosmicCardGradient,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_fullscreen),
                contentDescription = "Fullscreen",
                modifier = Modifier.size(CosmicSize.iconS),
                tint = CosmicTitleColor
            )
        }

        if(fullscreen){
            Dialog(
                onDismissRequest = {fullscreen = false},
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ){
                    AsyncImage(
                        model = url,
                        contentDescription = "Fullscreen Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )

                    IconButton(
                        onClick = { fullscreen = false },
                        modifier = Modifier
                            .padding(top = CosmicSize.large, end = CosmicSize.medium)
                            .align(Alignment.TopEnd)

                            .background(
                                color = CosmicCardGradient,
                                shape = CircleShape
                            )
                            .size(CosmicSize.large)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "Close",
                            tint = CosmicTitleColor,
                            modifier = Modifier
                                .size(CosmicSize.iconS)
                        )
                    }
                }
            }
        }
    }
}