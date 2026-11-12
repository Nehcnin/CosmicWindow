package com.example.cosmicwindow.feature.explore.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.example.cosmicwindow.core.ui.theme.CosmicCardGradient
import com.example.cosmicwindow.core.ui.theme.CosmicColors
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.explore.domain.model.ExploreItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExploreCard(
    item: ExploreItem, height: Dp
) {

    var textColor by remember(item.imageUrl) {
        mutableStateOf(Color.White)
    }

    var isImageLoading by remember(item.imageUrl) {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    val transition = rememberInfiniteTransition(label = "pulse")

    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(CosmicTheme.sizes.radiusXL))
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isImageLoading) 0f else 1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(item.imageUrl).allowHardware(false).build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    onLoading = { isImageLoading = true },
                    modifier = Modifier.fillMaxSize(),
                    onSuccess = { state ->
                        isImageLoading = false
                        val bitmap = state.result.image.toBitmap()

                        val palette = Palette.from(bitmap).generate()

                        val color = palette.getVibrantColor(
                            palette.getDominantColor(
                                android.graphics.Color.WHITE
                            )
                        )
                        textColor = Color(color).ensureContrast(CosmicCardGradient)
                    })
                Text(
                    text = item.id,
                    style = CosmicTheme.typography.bodySmallMedium,
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(CosmicTheme.sizes.smallL)
                        .align(alignment = Alignment.TopStart)
                        .background(
                            color = CosmicTheme.colors.backgroundVariant,
                            shape = CircleShape
                        )
                        .padding(horizontal = CosmicTheme.sizes.smallL, vertical = CosmicTheme.sizes.smallS)
                        .widthIn(max = CosmicTheme.sizes.largeXL)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CosmicTheme.sizes.largeXL)
                    .background(color = CosmicTheme.colors.surface)
            ) {
                Text(
                    text = item.title,
                    style = CosmicTheme.typography.headerTitle,
                    color = CosmicTheme.colors.whitePrimary,
                    modifier = Modifier
                        .padding(CosmicTheme.sizes.smallL)
                        .align(alignment = Alignment.TopStart),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = LocalDate.parse(item.date.substringBefore("T"))
                        .format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)),
                    style = CosmicTheme.typography.bodySmall,
                    color = CosmicTheme.colors.bodyGray,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(CosmicTheme.sizes.smallL),
                )

                Text(
                    text = item.center,
                    style = CosmicTheme.typography.bodySmall,
                    color = textColor,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(CosmicTheme.sizes.smallL)
                )
            }
        }
        if (isImageLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clip(RoundedCornerShape(CosmicTheme.sizes.radiusXL))
                    .background(CosmicTheme.colors.surface.copy(alpha = alpha))
            )
        }
    }
}

fun Color.ensureContrast(
    background: Color,
    minContrast: Double = 4.5
): Color {
    val bg = background.toArgb()
    val original = this.toArgb()

    if (ColorUtils.calculateContrast(original, bg) >= minContrast) {
        return this
    }

    val white = android.graphics.Color.WHITE
    val black = android.graphics.Color.BLACK

    val target =
        if (ColorUtils.calculateContrast(white, bg) > ColorUtils.calculateContrast(black, bg)) white else black

    var result = original

    for (i in 1..100) {
        result = ColorUtils.blendARGB(original, target, i / 100f)

        if (ColorUtils.calculateContrast(result, bg) >= minContrast) {
            return Color(result)
        }

    }

    return Color(target)
}
