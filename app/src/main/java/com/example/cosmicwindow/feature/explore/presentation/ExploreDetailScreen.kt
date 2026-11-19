package com.example.cosmicwindow.feature.explore.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import com.example.cosmicwindow.feature.explore.presentation.components.ExpandableDescription
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExploreDetailScreen(item: ExploreItem, onBack: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = item.imageUrl,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        IconButton(
            onClick = onBack,
            modifier =
                Modifier
                    .padding(CosmicTheme.sizes.medium)
                    .align(Alignment.TopStart)
                    .background(
                        color = CosmicTheme.colors.backgroundVariant,
                        shape = CircleShape,
                    ).size(CosmicSize.large),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Close",
                tint = CosmicTheme.colors.whitePrimary,
                modifier =
                    Modifier
                        .size(CosmicSize.iconS),
            )
        }

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colorStops =
                                    arrayOf(
                                        0f to Color.Transparent,
                                        0.7f to Color.Transparent,
                                        0.9f to CosmicTheme.colors.darkPrimary,
                                        1f to CosmicTheme.colors.darkPrimary,
                                    ),
                            ),
                    ),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
        ) {

            ExpandableDescription(title = item.title, description = item.description, modifier = Modifier.weight(1f))

            Row(
                modifier =
                    Modifier
                        .padding(horizontal = CosmicTheme.sizes.medium)
                        .padding(bottom = CosmicTheme.sizes.medium, top = CosmicTheme.sizes.small)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(CosmicTheme.sizes.radiusL))
                        .background(CosmicTheme.colors.backgroundDark)
                        .padding(CosmicTheme.sizes.mediumS),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.small),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Observation Date",
                        style = CosmicTheme.typography.bodySmallMedium,
                        color = CosmicTheme.colors.bodyGray,
                    )
                    Text(
                        text =
                            LocalDate
                                .parse(item.date.substringBefore("T"))
                                .format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)),
                        style = CosmicTheme.typography.bodySmall,
                        color = CosmicTheme.colors.whitePrimary,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.small),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Center",
                        style = CosmicTheme.typography.bodySmallMedium,
                        color = CosmicTheme.colors.bodyGray,
                    )
                    Text(
                        text = item.center,
                        style = CosmicTheme.typography.bodySmall,
                        color = CosmicTheme.colors.whitePrimary,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(CosmicTheme.sizes.small),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "NASA ID",
                        style = CosmicTheme.typography.bodySmallMedium,
                        color = CosmicTheme.colors.bodyGray,
                    )
                    Text(
                        text = item.id,
                        style = CosmicTheme.typography.bodySmall,
                        color = CosmicTheme.colors.whitePrimary,
                    )
                }
            }
        }
    }
}
