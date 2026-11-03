package com.example.cosmicwindow.feature.today.presentation.components.cards

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicButtonGray
import com.example.cosmicwindow.core.ui.theme.CosmicCardGradient
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.feature.today.domain.model.Apod
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TodayApodCard(apod: Apod, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val date = LocalDate.parse(apod.date)

    val formattedDate = date.format(
        DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
    ).uppercase(Locale.ENGLISH)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(CosmicSize.cardXXL),
        shape = RoundedCornerShape(CosmicSize.radiusXL)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = apod.imgUrl,
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
                                0.0f to Color.Transparent,
                                1f to CosmicCardGradient
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(CosmicSize.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelMedium,
                        color = CosmicNavTextSelected,
                    )
                    if(apod.credit != null) {
                        Text(
                            modifier = Modifier.padding(horizontal = CosmicSize.smallL),
                            text = "•",
                            style = MaterialTheme.typography.labelMedium,
                            color = CosmicIconGray,
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            modifier = Modifier.size(CosmicSize.mediumS),
                            contentDescription = "",
                            tint = CosmicIconGray
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = CosmicSize.smallL),
                            text = apod.credit,
                            style = MaterialTheme.typography.labelMedium,
                            color = CosmicIconGray,
                        )
                    }
                }

                Text(
                    text = apod.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = CosmicSize.smallL)
                )

                if (apod.alt != null) {
                    Text(
                        text = apod.alt,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 3,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = CosmicSize.medium)
                        .fillMaxWidth()
                        .height(CosmicSize.largeL),
                    horizontalArrangement = Arrangement.spacedBy(CosmicSize.smallL),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(9999.dp)
                            )
                            .padding(
                                horizontal = CosmicSize.mediumL
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Read Cosmic Story",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_button_rarrow),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = CosmicSize.smallL)
                                .size(CosmicSize.mediumS)
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(CosmicSize.largeL)
                            .background(
                                color = CosmicButtonGray,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_nav_saved),
                            contentDescription = "",
                            tint = CosmicIconGray,
                            modifier = Modifier.size(CosmicSize.iconM)
                        )
                    }

                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    apod.articleUrl
                                )
                            }

                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share APOD")
                            )

                        },
                        modifier = Modifier
                            .size(CosmicSize.largeL)
                            .background(
                                color = CosmicButtonGray,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = "",
                            tint = CosmicIconGray,
                            modifier = Modifier.size(CosmicSize.iconM)

                        )
                    }
                }
            }
        }

    }

}