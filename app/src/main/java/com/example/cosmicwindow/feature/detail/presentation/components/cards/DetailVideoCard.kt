package com.example.cosmicwindow.feature.detail.presentation.components.cards

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicButtonGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.today.domain.model.Apod

@Composable
fun DetailVideoCard(loadedApod: Apod, context: Context) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(CosmicSize.medium)
                .height(CosmicSize.cardXXS)
                .background(
                    color = CosmicTheme.colors.surface,
                    shape = RoundedCornerShape(CosmicSize.radiusL),
                ).border(
                    width = 1.dp,
                    shape = RoundedCornerShape(CosmicSize.radiusL),
                    color = CosmicSurfaceBorder,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(start = CosmicSize.medium)
                    .background(
                        color = CosmicButtonGray,
                        shape = CircleShape,
                    ),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_video_play),
                tint = CosmicNavTextSelected,
                modifier =
                    Modifier
                        .padding(CosmicSize.mediumS)
                        .size(CosmicSize.iconM),
                contentDescription = "",
            )
        }

        Column(
            modifier =
                Modifier
                    .padding(start = CosmicSize.smallL),
            verticalArrangement = Arrangement.spacedBy(CosmicSize.small),
        ) {
            Text(
                text = "Video Version Available",
                style = CosmicTheme.typography.bodyMediumSemi,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
                modifier =
                    Modifier.basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 5000,
                        repeatDelayMillis = 5000,
                    ),
                color = CosmicTheme.colors.whitePrimary
            )
            Text(
                text = "External host",
                style = CosmicTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
                modifier =
                    Modifier.basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 5000,
                        repeatDelayMillis = 5000,
                    ),
                color = CosmicTheme.colors.bodyGray
            )
        }
        Spacer(
            modifier = Modifier.weight(1f),
        )
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = CosmicButtonGray,
                        shape = RoundedCornerShape(CosmicSize.radiusM),
                    ).padding(horizontal = CosmicSize.medium)
                    .padding(vertical = CosmicSize.mediumS)
                    .clickable(
                        onClick = {
                            loadedApod.videoUrl?.let { url ->
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW, url.toUri()),
                                )
                            }
                        },
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Open video",
                style = CosmicTheme.typography.bodySmallBold,
                color = CosmicTheme.colors.selected,
            )
            Icon(
                painter = painterResource(R.drawable.ic_external),
                tint = CosmicTheme.colors.selected,
                modifier =
                    Modifier
                        .padding(start = CosmicSize.small)
                        .size(CosmicSize.iconS),
                contentDescription = "",
            )
        }
    }
}
