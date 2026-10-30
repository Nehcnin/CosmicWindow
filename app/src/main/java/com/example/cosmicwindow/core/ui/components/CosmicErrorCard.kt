package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicErrorBackground
import com.example.cosmicwindow.core.ui.theme.CosmicErrorIcon
import com.example.cosmicwindow.core.ui.theme.CosmicSize

@Composable
fun CosmicErrorCard(
    modifier: Modifier = Modifier,
    callback: () -> Unit
)
{

    Box(
        modifier = Modifier
            .padding(horizontal = CosmicSize.medium)
            .height(CosmicSize.cardXXS)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(CosmicSize.radiusXL)
            )
            .border(
                width = 1.dp,
                color = CosmicErrorBackground,
                shape = RoundedCornerShape(CosmicSize.radiusXL)
            )
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = CosmicSize.medium)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CosmicSize.small)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_error_cloud),
                contentDescription = "",
                tint = CosmicErrorIcon,
                modifier = Modifier
                    .size(CosmicSize.iconM)
            )
            Text(
                text = "Unable to reach NASA archive. Check connection.",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = CosmicSize.smallL)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 2000,
                        velocity = 30.dp
                    ),
                softWrap = false,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.bodyMedium
            )

            CosmicIconButton(
                icon = R.drawable.retry,
                text = "Retry",
                color = CosmicErrorBackground,
                textColor = CosmicErrorIcon,
                callback = callback
            )
        }
    }
}