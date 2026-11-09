package com.example.cosmicwindow.feature.today.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cosmicwindow.core.ui.theme.CosmicCardGradient
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.core.ui.theme.CosmicTitleColor
import com.example.cosmicwindow.feature.today.domain.model.Apod
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TodayWeekCard(apod: Apod, dayText: String) {
    Column(
        modifier =
            Modifier
                .fillMaxHeight()
                .width(CosmicSize.cardL)
                .background(
                    color = CosmicTheme.colors.surface,
                    shape = RoundedCornerShape(CosmicSize.radiusXL),
                ).border(
                    width = 1.dp,
                    color = CosmicSurfaceBorder,
                    shape = RoundedCornerShape(CosmicSize.radiusXL),
                ).padding(CosmicSize.mediumS),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier =
                Modifier
                    .height(CosmicSize.cardS)
                    .clip(shape = RoundedCornerShape(CosmicSize.mediumS)),
        ) {
            AsyncImage(
                model = apod.imgUrl,
                contentDescription = "",
                modifier =
                    Modifier
                        .height(CosmicSize.cardS)
                        .clip(shape = RoundedCornerShape(CosmicSize.mediumS)),
                contentScale = ContentScale.Crop,
            )

            Text(
                text =
                    LocalDate
                        .parse(apod.date)
                        .format(DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)),
                modifier =
                    Modifier
                        .padding(CosmicSize.smallL)
                        .align(Alignment.TopStart)
                        .background(
                            color = CosmicCardGradient,
                            shape = RoundedCornerShape(CosmicSize.radiusRound),
                        ).padding(CosmicSize.mediumS),
                style = CosmicTheme.typography.bodyMedium,
                color = CosmicTheme.colors.bodyGray
            )
        }
        Text(
            text = dayText,
            style = CosmicTheme.typography.bodyMedium,
            color = CosmicTheme.colors.bodyGray
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .basicMarquee(
                        initialDelayMillis = 8000,
                        repeatDelayMillis = 8000,
                    ),
            text = apod.title,
            style = CosmicTheme.typography.bodyMediumS,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            softWrap = false,
            color = CosmicTheme.colors.whitePrimary,
        )
    }
}
