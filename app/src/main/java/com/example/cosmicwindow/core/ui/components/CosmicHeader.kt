package com.example.cosmicwindow.core.ui.components

import android.app.DatePickerDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.core.ui.theme.CosmicSize
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import java.time.LocalDate

@Composable
fun CosmicHeader(
    title: String,
    subtitle: String,
    @DrawableRes firstIcon: Int,
    @DrawableRes lastIcon: Int,
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
    val context = LocalContext.current

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
                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    onDateSelected?.invoke(
                                        LocalDate.of(year, month + 1, day),
                                    )
                                },
                                selectedDate.year,
                                selectedDate.monthValue - 1,
                                selectedDate.dayOfMonth,
                            ).apply {
                                datePicker.maxDate = System.currentTimeMillis()
                            }.show()
                        } else {
                            onLastIconClick?.invoke()
                        }
                    },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(lastIcon),
                contentDescription = "Header action",
                modifier = Modifier.size(lastIconSize),
                tint = tintLast,
            )
        }
    }
}
