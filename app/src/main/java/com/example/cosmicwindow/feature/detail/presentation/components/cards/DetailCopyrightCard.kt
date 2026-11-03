package com.example.cosmicwindow.feature.detail.presentation.components.cards

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicYellow

@Composable
fun DetailCopyrightCard(
    credit: String,
    copyright: String,
    url: String,
    context : Context
) {
    Column(
        modifier = Modifier
            .padding(CosmicSize.medium)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(CosmicSize.radiusL)
            )
            .border(
                width = 1.dp,
                color = CosmicSurfaceBorder,
                shape = RoundedCornerShape(CosmicSize.radiusL)
            )
            .padding(CosmicSize.medium)
    ) {
        Text(
            text = "ATTRIBUTION & SOURCE",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = CosmicNavTextSelected
        )

        Row(
            modifier = Modifier
                .padding(vertical = CosmicSize.mediumS)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Source",
                style = MaterialTheme.typography.labelLarge,
                color = CosmicIconGray
            )
            Text(
                text = "NASA APOD API",
                style = MaterialTheme.typography.labelLarge,
                fontSize = 16.sp
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = CosmicSurfaceBorder
        )

        Row(
            modifier = Modifier
                .padding(vertical = CosmicSize.mediumS)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Credit",
                style = MaterialTheme.typography.labelLarge,
                color = CosmicIconGray
            )
            Text(
                text = credit,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 16.sp
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = CosmicSurfaceBorder
        )

        Row(
            modifier = Modifier
                .padding(vertical = CosmicSize.mediumS)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Copyright",
                style = MaterialTheme.typography.labelLarge,
                color = CosmicIconGray
            )
            Text(
                text = copyright,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 16.sp,
                color = CosmicYellow
            )
        }

        Row(
            modifier = Modifier
                .padding(top = CosmicSize.medium)
                .fillMaxWidth()
                .height(CosmicSize.large)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(CosmicSize.radiusL)
                )
                .clip(RoundedCornerShape(CosmicSize.radiusL))
                .clickable {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, url.toUri())
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "View original APOD entry",
                style = MaterialTheme.typography.labelLarge,
                color = CosmicNavTextSelected,
                fontWeight = FontWeight.SemiBold
            )

            Icon(
                painter = painterResource(R.drawable.ic_external),
                tint = CosmicNavTextSelected,
                modifier = Modifier
                    .padding(start = CosmicSize.smallL)
                    .size(CosmicSize.iconS),
                contentDescription = null
            )
        }

    }
}
