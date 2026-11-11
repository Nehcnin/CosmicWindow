package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TodayDateComponent(date: LocalDate?, modifier: Modifier = Modifier) {
    val formattedDate =
        date
            ?.format(
                DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH),
            )?.uppercase(Locale.ENGLISH)

    Row(
        modifier
            .fillMaxWidth()
            .padding(bottom = CosmicTheme.sizes.medium)
            .padding(horizontal = CosmicTheme.sizes.medium)
            .height(CosmicTheme.sizes.largeS)
            .requiredHeight(
                CosmicSize.largeS,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(end = 8.dp)
                    .size(8.dp)
                    .background(
                        color = CosmicNavTextSelected,
                        shape = CircleShape,
                    ),
        )
        if (formattedDate != null) {
            Text(
                text = "Today in Space • $formattedDate",
                style = CosmicTheme.typography.bodyMedium,
                color = CosmicTheme.colors.bodyGray,
            )
        }
    }
}
