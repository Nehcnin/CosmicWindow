package com.example.cosmicwindow.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import java.time.LocalDate

@Composable
fun CosmicHeader(
    title: String,
    subtitle: String,
    @DrawableRes firstIcon: Int,
    @DrawableRes lastIcon: Int? = null,
    onLastIconClick: (() -> Unit)? = null,
    showDatePicker: Boolean = false,
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: ((LocalDate) -> Unit)? = null,
    firstIconSize: Dp = 64.dp,
    lastIconSize: Dp = 24.dp,
    modifierFirst: Modifier = Modifier,
    tintLast: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
) {
    var datePickerOpen by remember { mutableStateOf(false) }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(firstIcon),
            contentDescription = null,
            modifier = modifierFirst.size(firstIconSize),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier.offset(y = (-4).dp),
        ) {
            Text(
                text = title,
                style = CosmicTheme.typography.headerTitle,
                modifier = Modifier.padding(bottom = 4.dp),
                color = CosmicTheme.colors.whitePrimary,
            )

            Text(
                text = subtitle,
                style = CosmicTheme.typography.headerBody,
                color = CosmicTheme.colors.bodyGray,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier =
                Modifier
                    .padding(end = CosmicSize.medium)
                    .size(48.dp)
                    .clickable {
                        if (showDatePicker) {
                            datePickerOpen = true
                        } else {
                            onLastIconClick?.invoke()
                        }
                    },
            contentAlignment = Alignment.Center,
        ) {
            if (lastIcon != null) {
                Icon(
                    painter = painterResource(lastIcon),
                    contentDescription = stringResource(R.string.common_header_action),
                    modifier = Modifier.size(lastIconSize),
                    tint = tintLast,
                )
            }
        }
    }

    if (datePickerOpen) {
        CosmicDatePickerDialog(
            changeDatePickerOpen = { datePickerOpen = it },
            datePickerOpen = datePickerOpen,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
        )
    }
}
