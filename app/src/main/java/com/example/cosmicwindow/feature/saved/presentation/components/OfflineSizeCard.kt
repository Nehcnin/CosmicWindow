package com.example.cosmicwindow.feature.saved.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import kotlin.math.roundToInt

@Composable
fun OfflineSizeCard(count: Int, size: Double) {
    val rounded = (size * 100).roundToInt() / 100.0
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(CosmicTheme.sizes.radiusL))
                .background(
                    color = CosmicTheme.colors.surface,
                ).padding(CosmicTheme.sizes.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(end = CosmicTheme.sizes.smallL)
                    .size(CosmicTheme.sizes.iconXL)
                    .clip(CircleShape)
                    .background(CosmicTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_downloaded),
                contentDescription = "",
                tint = CosmicTheme.colors.onPrimary,
                modifier = Modifier.size(CosmicTheme.sizes.iconS),
            )
        }

        Column {
            Text(
                text = stringResource(R.string.saved_offline_ready),
                style = CosmicTheme.typography.bodyMediumSemi,
                color = CosmicTheme.colors.whitePrimary,
            )

            Text(
                text = stringResource(R.string.saved_discoveries_synced, count),
                style = CosmicTheme.typography.bodySmall,
                color = CosmicTheme.colors.bodyGray,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .clip(CircleShape)
                    .background(color = CosmicTheme.colors.surfaceTertiary)
                    .padding(CosmicTheme.sizes.smallL),
        ) {
            Box(
                modifier =
                    Modifier
                        .padding(end = CosmicTheme.sizes.smallL)
                        .size(CosmicTheme.sizes.smallL)
                        .clip(CircleShape)
                        .background(color = CosmicTheme.colors.selected),
            )

            Text(
                text = stringResource(R.string.saved_storage_size, rounded),
                style = CosmicTheme.typography.bodySmallMedium,
                color = CosmicTheme.colors.selected,
            )
        }
    }
}
