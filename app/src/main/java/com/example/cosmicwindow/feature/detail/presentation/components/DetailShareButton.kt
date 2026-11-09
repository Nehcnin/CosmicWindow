package com.example.cosmicwindow.feature.detail.presentation.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmicwindow.core.ui.theme.CosmicButtonGray
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun DetailShareButton(context: Context, url: String) {
    Box(
        modifier =
            Modifier
                .padding(CosmicSize.medium)
                .fillMaxWidth()
                .height(CosmicSize.cardXXS)
                .clip(shape = RoundedCornerShape(CosmicSize.radiusRound))
                .background(CosmicTheme.colors.bodyGrayVariant)
                .border(
                    width = 1.dp,
                    color = CosmicTheme.colors.border,
                    shape = RoundedCornerShape(CosmicSize.radiusRound),
                ).clickable(
                    onClick = {
                        val shareIntent =
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    url,
                                )
                            }

                        context.startActivity(
                            Intent.createChooser(shareIntent, "Share APOD"),
                        )
                    },
                ),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(CosmicSize.smallL)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(CosmicSize.radiusRound))
                    .background(CosmicTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Share Story",
                style = CosmicTheme.typography.bodyMedium,
                color = CosmicTheme.colors.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
    }
}
