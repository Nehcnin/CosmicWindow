package com.example.cosmicwindow.feature.today.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicCardGradient
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder

@Composable
fun TodayCopyrightCard(
    copyright: String,
    credit: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CosmicSize.radiusXL))
            .background(CosmicCardGradient)
            .border(
                width = 1.dp,
                color = CosmicSurfaceBorder,
                shape = RoundedCornerShape(CosmicSize.radiusXL)
            )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = CosmicSize.medium),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                modifier = Modifier.padding(end = CosmicSize.smallL).size(CosmicSize.iconM),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Public NASA Data • APOD Archive",
                style = MaterialTheme.typography.labelLarge
            )
        }
        Text(
            text = "Image Credit: $credit, NASA • Copyright $copyright",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = CosmicSize.mediumL).padding(top = CosmicSize.medium).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Independent educational explorer • Not affiliated with NASA",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = CosmicSize.medium)
                .padding(bottom = CosmicSize.medium)
                .fillMaxWidth()
                .padding(top = CosmicSize.medium)
                .clip(RoundedCornerShape(CosmicSize.radiusRound))
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    horizontal = CosmicSize.medium,
                    vertical = CosmicSize.medium
                )
        )

    }
}