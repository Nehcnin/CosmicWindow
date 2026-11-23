package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun CosmicSnackbar(
    message: String,
    onUndo: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(CosmicTheme.sizes.medium)
            .clip(RoundedCornerShape(CosmicTheme.sizes.radiusL))
            .border(width = 1.dp, color = CosmicTheme.colors.border, shape = RoundedCornerShape(CosmicTheme.sizes.radiusL))
            .background(CosmicTheme.colors.surfaceTertiary)
            .padding(CosmicTheme.sizes.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_info),
            contentDescription = "",
            tint = CosmicTheme.colors.selected,
            modifier = Modifier
                .padding(end = CosmicTheme.sizes.smallL)
                .size(CosmicTheme.sizes.iconS)
        )

        Text(
            text = message,
            style = CosmicTheme.typography.bodyMedium,
            color = CosmicTheme.colors.whitePrimary,
            modifier = Modifier.padding(CosmicTheme.sizes.small)
        )

        Spacer(modifier = Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .clip(RoundedCornerShape(CosmicTheme.sizes.radiusM))
                .clickable(
                    onClick = { onUndo() }
            )
        ) {
            Text(
                text = "Undo",
                style = CosmicTheme.typography.bodyMedium,
                color = CosmicTheme.colors.selected,
                modifier = Modifier.padding(CosmicTheme.sizes.small)
            )
        }
    }
}
