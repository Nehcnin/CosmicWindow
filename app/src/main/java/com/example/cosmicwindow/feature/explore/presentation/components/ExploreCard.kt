package com.example.cosmicwindow.feature.explore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Instant

@Composable
fun ExploreCard(
    item: ExploreItem,
    height: Dp
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(CosmicTheme.sizes.radiusXL))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ){
            AsyncImage(
                model = item.imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Text(
                text = item.id,
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicTheme.colors.whitePrimary,
                modifier = Modifier.padding(CosmicTheme.sizes.medium).align(alignment = Alignment.TopStart)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CosmicTheme.sizes.largeXL)
                .background(color = CosmicTheme.colors.surface)
        ){
            Text(
                text = item.title,
                style = CosmicTheme.typography.headerTitle,
                color = CosmicTheme.colors.whitePrimary,
                modifier = Modifier.padding(CosmicTheme.sizes.smallL).align(alignment = Alignment.TopStart),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = LocalDate.parse(item.date.substringBefore("T"))
                    .format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)),
                style = CosmicTheme.typography.bodySmall,
                color = CosmicTheme.colors.bodyGray,
                modifier = Modifier.align(Alignment.BottomStart),
            )

            Text(
                text = item.center,
                style = CosmicTheme.typography.bodySmall,
                color = CosmicTheme.colors.bodyGray,
                modifier = Modifier.align(alignment = Alignment.BottomEnd)
            )
        }
    }
}
