package com.example.cosmicwindow.feature.saved.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.saved.domain.model.SavedApod
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SavedApodCard(
    apod: SavedApod,
    onDeleteSaved: (SavedApod) -> Unit,
    onReadStory: (String, Boolean) -> Unit,
) {
    val locale = LocalConfiguration.current.locales[0]
    val context = LocalContext.current

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(CosmicTheme.sizes.savedCard)
                .clip(RoundedCornerShape(CosmicTheme.sizes.radiusL))
                .background(CosmicTheme.colors.surfaceSecondary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(CosmicTheme.sizes.card),
        ) {
            AsyncImage(
                model = File(context.filesDir, apod.localImagePath),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "",
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, CosmicTheme.colors.surfaceSecondary),
                                ),
                        ),
            )

            IconButton(
                onClick = { onDeleteSaved(apod) },
                modifier =
                    Modifier
                        .padding(CosmicTheme.sizes.smallL)
                        .size(CosmicSize.large)
                        .clip(CircleShape)
                        .background(
                            color = CosmicTheme.colors.backgroundDark,
                        ).align(Alignment.TopEnd),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_saved_full),
                    contentDescription = "",
                    tint = CosmicTheme.colors.selected,
                    modifier = Modifier.size(CosmicSize.iconS),
                )
            }

            Row(
                modifier =
                    Modifier
                        .padding(CosmicTheme.sizes.mediumS)
                        .clip(CircleShape)
                        .background(CosmicTheme.colors.backgroundDark)
                        .padding(horizontal = CosmicTheme.sizes.mediumS, vertical = CosmicTheme.sizes.smallL),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_book),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(end = CosmicTheme.sizes.smallL)
                            .size(CosmicTheme.sizes.mediumS),
                    tint = CosmicTheme.colors.selected,
                )

                Text(
                    text = stringResource(R.string.saved_apod_story),
                    style = CosmicTheme.typography.bodySmallMedium,
                    color = CosmicTheme.colors.selected,
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CosmicTheme.sizes.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = null,
                tint = CosmicTheme.colors.selected,
                modifier = Modifier
                    .padding(end = CosmicTheme.sizes.smallL)
                    .size(CosmicTheme.sizes.mediumS)
            )

            Text(
                text = apod.credit,
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicTheme.colors.bodyGray,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = LocalDate.parse(apod.date).format(
                    DateTimeFormatter.ofPattern("MMM dd, yyyy", locale)
                ),
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicTheme.colors.bodyGray,
                modifier = Modifier.padding(start = CosmicTheme.sizes.smallL),
                maxLines = 1,
                softWrap = false
            )
        }

        Text(
            text = apod.title,
            style = CosmicTheme.typography.headerTitle,
            color = CosmicTheme.colors.whitePrimary,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CosmicTheme.sizes.medium),
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 1,
        )

        Text(
            text = apod.alt,
            style = CosmicTheme.typography.bodyMedium.copy(fontSize = 14.sp, lineHeight = 20.sp),
            color = CosmicTheme.colors.bodyGray,
            modifier =
                Modifier
                    .padding(top = CosmicTheme.sizes.smallL, bottom = CosmicTheme.sizes.smallL)
                    .fillMaxWidth()
                    .padding(horizontal = CosmicTheme.sizes.medium),
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            maxLines = 2,
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = CosmicSurfaceBorder,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CosmicTheme.sizes.medium),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = CosmicTheme.sizes.medium),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_checkmark),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(end = CosmicTheme.sizes.smallL)
                            .size(CosmicTheme.sizes.mediumS),
                    tint = CosmicTheme.colors.selected,
                )

                Text(
                    text =
                        stringResource(
                            R.string.saved_offline_date,
                            LocalDate.parse(apod.saveDate).format(
                                DateTimeFormatter.ofPattern(
                                    "MMM dd",
                                    locale,
                                ),
                            ),
                        ),
                    style = CosmicTheme.typography.bodySmallMedium,
                    color = CosmicTheme.colors.bodyGray,
                )
            }

            Row(
                modifier =
                    Modifier
                        .padding(
                            horizontal = CosmicSize.mediumL,
                        ).clickable(onClick = { onReadStory(apod.date, true) })
                        .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.common_read_cosmic_story),
                    style = CosmicTheme.typography.bodySmallMedium.copy(fontSize = 12.sp),
                    color = CosmicTheme.colors.selected,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_button_rarrow),
                    tint = CosmicTheme.colors.selected,
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(start = CosmicSize.smallL)
                            .size(CosmicSize.mediumS),
                )
            }
        }
    }
}
