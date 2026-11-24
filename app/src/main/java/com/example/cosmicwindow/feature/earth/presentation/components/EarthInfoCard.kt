package com.example.cosmicwindow.feature.earth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun EarthInfoCard() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = CosmicSize.medium)
                .background(
                    color = CosmicTheme.colors.surface,
                    shape = RoundedCornerShape(CosmicSize.radiusL),
                ).padding(CosmicSize.medium),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_info),
            modifier =
                Modifier
                    .padding(end = CosmicSize.smallL)
                    .size(CosmicSize.iconS),
            contentDescription = "",
            tint = CosmicTheme.colors.selected,
        )

        Text(
            text =
                stringResource(R.string.earth_info_description),
            style = CosmicTheme.typography.bodyMedium,
            color = CosmicTheme.colors.bodyGray,
        )
    }
}
