package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayWeekCard

@Composable
fun TodayWeekRow(
    viewModel: TodayViewModel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(256.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = CosmicSize.medium)
                .padding(bottom = CosmicSize.mediumS)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bars),
                tint = CosmicNavTextSelected,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = CosmicSize.smallL)
                    .size(CosmicSize.iconM)
            )

            Text(
                text = "Previous 7 Days",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Past APOD Entries",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        LazyRow(
            modifier = Modifier.padding(start = CosmicSize.medium),
            horizontalArrangement = Arrangement.spacedBy(CosmicSize.smallL)
        ) {

            itemsIndexed(viewModel.apods) { index, apod ->
                if (index > 0) {
                    val dayText = if (index == 1) "Yesterday" else "$index days ago"
                    TodayWeekCard(
                        apod = apod,
                        dayText = dayText
                    )
                }
            }
        }
    }
}