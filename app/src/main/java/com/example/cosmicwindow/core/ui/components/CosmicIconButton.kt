package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme

@Composable
fun CosmicIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
    color: Color,
    textColor: Color,
    callback: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(50),
                ).clickable(onClick = callback)
                .padding(CosmicSize.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CosmicSize.smallL),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(CosmicSize.iconS),
        )

        Text(
            text = text,
            color = textColor,
            style = CosmicTheme.typography.bodySmall,
            maxLines = 1,
            softWrap = false,
        )
    }
}
