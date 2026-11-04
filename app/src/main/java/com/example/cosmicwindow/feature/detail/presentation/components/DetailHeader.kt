package com.example.cosmicwindow.feature.detail.presentation.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTitleColor

@Composable
fun DetailHeader(
    onBack: () -> Unit,
    context: Context,
    url: String?,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = CosmicSize.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(
            modifier =
                Modifier
                    .padding(end = CosmicSize.medium)
                    .size(CosmicSize.iconS),
            onClick = onBack,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_button_rarrow),
                tint = CosmicTitleColor,
                contentDescription = "backButton",
                modifier =
                    Modifier
                        .size(CosmicSize.iconS)
                        .rotate(180f),
            )
        }

        Text(
            text = "APOD Story Detail",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = {},
            modifier =
                Modifier
                    .size(CosmicSize.largeL),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_nav_saved),
                contentDescription = "",
                tint = CosmicIconGray,
                modifier = Modifier.size(CosmicSize.iconM),
            )
        }

        IconButton(
            onClick = {
                val shareIntent =
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            url,
                        )
                    }

                context.startActivity(
                    Intent.createChooser(shareIntent, "Share APOD"),
                )
            },
            modifier =
                Modifier
                    .size(CosmicSize.largeL),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "",
                tint = CosmicIconGray,
                modifier = Modifier.size(CosmicSize.iconM),
            )
        }
    }
}
