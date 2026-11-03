package com.example.cosmicwindow.feature.today.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicOnYellow
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicYellow
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.presentation.components.TodayVideoPlayer
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.platform.LocalContext

@Composable
fun TodayVideoCard(apod: Apod, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(
                    size = CosmicSize.radiusXL
                )
            )
            .border(
                width = 1.dp,
                color = CosmicSurfaceBorder,
                shape = RoundedCornerShape(size = CosmicSize.radiusXL)
            )
            .padding(CosmicSize.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_video),
                contentDescription = "",
                tint = CosmicYellow,
                modifier = Modifier
                    .padding(end = CosmicSize.smallL)
                    .size(CosmicSize.iconS)
            )

            Text(
                text = "Featured APOD Video",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .background(
                        color = CosmicOnYellow,
                        shape = RoundedCornerShape(size = CosmicSize.radiusRound)
                    )
                    .padding(CosmicSize.mediumS),
                text = "External Media",
                color = CosmicYellow,
                style = MaterialTheme.typography.labelLarge
            )
        }
        if (apod.videoUrl != null && apod.imgUrl != null) {
            TodayVideoPlayer(
                videoUrl = apod.videoUrl,
                thumbnailUrl = apod.imgUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = CosmicSize.medium)
                    .height(CosmicSize.cardXL)
            )
        }

        Text(
            text = apod.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = CosmicSize.medium)
        )
        if(apod.alt!=null) {
            Text(
                text = apod.alt,
                style = MaterialTheme.typography.bodyLarge
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = CosmicSize.medium)
                .height(CosmicSize.large)
                .clip(RoundedCornerShape(CosmicSize.radiusL))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 1.dp,
                    color = CosmicSurfaceBorder,
                    shape = RoundedCornerShape(CosmicSize.radiusL)
                )
                .clickable {
                    apod.articleUrl?.let { url ->
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Open video in browser",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    painter = painterResource(R.drawable.ic_external),
                    modifier = Modifier
                        .padding(start = CosmicSize.smallL)
                        .size(CosmicSize.iconS),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }    }
}