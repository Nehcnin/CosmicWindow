package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import com.example.cosmicwindow.feature.today.presentation.components.cards.TodayWeekCard
import java.time.LocalDate

@Composable
fun TodayWeekRow(viewModel: TodayViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(256.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = CosmicSize.medium)
                    .padding(bottom = CosmicSize.mediumS)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bars),
                tint = CosmicNavTextSelected,
                contentDescription = "",
                modifier =
                    Modifier
                        .padding(end = CosmicSize.smallL)
                        .size(CosmicSize.iconM),
            )

            Text(
                text = stringResource(R.string.today_previous_days),
                style = CosmicTheme.typography.titleMediumS,
                color = CosmicTheme.colors.whitePrimary,
            )



            Text(
                text = stringResource(R.string.today_past_entries),
                style = CosmicTheme.typography.bodySmall,
                color = CosmicTheme.colors.bodyGray,
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(CosmicSize.smallL),
            contentPadding =
                PaddingValues(
                    horizontal = CosmicSize.medium,
                ),
        ) {
            itemsIndexed(viewModel.apods.drop(1)) { index, apod ->
                val dayText = if (index == 0) stringResource(R.string.today_yesterday) else stringResource(R.string.today_days_ago, index)
                TodayWeekCard(
                    apod = apod,
                    dayText = dayText,
                    onClick = {
                        viewModel.updateDate(LocalDate.parse(apod.date))
                    }
                )
            }
        }
    }
}

