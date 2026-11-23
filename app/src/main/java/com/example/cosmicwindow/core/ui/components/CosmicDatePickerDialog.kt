package com.example.cosmicwindow.core.ui.components


import android.app.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset


@Composable
fun CosmicDatePickerDialog(
    changeDatePickerOpen: (Boolean) -> Unit,
    datePickerOpen: Boolean,
    selectedDate: LocalDate,
    onDateSelected: ((LocalDate) -> Unit)? = null
){

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis <= LocalDate.now()
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()
                    .toEpochMilli()

            override fun isSelectableYear(year: Int): Boolean =
                year <= LocalDate.now().year
        },
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = CosmicTheme.colors.backgroundDark
        ),
        onDismissRequest = { changeDatePickerOpen(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateSelected?.invoke(
                            Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                        )
                    }
                    changeDatePickerOpen(false)
                },
            ) {
                Text("OK", color = CosmicTheme.colors.selected)
            }
        },
        dismissButton = {
            TextButton(onClick = { changeDatePickerOpen(false) }) {
                Text("Cancel", color = CosmicTheme.colors.bodyGray)
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = CosmicTheme.colors.background,
                selectedDayContainerColor = CosmicTheme.colors.selected,
                selectedDayContentColor = CosmicTheme.colors.onPrimary,
                todayContentColor = CosmicTheme.colors.selected,
                todayDateBorderColor = CosmicTheme.colors.selected,
                dayContentColor = CosmicTheme.colors.whitePrimary,
                headlineContentColor = CosmicTheme.colors.whitePrimary,
                weekdayContentColor = CosmicTheme.colors.bodyGray,
                disabledYearContentColor = CosmicTheme.colors.bodyGray.copy(alpha = 0.5f),
                yearContentColor = CosmicTheme.colors.whitePrimary,
                navigationContentColor = CosmicTheme.colors.selected,
                selectedYearContentColor = CosmicTheme.colors.onPrimary,
                selectedYearContainerColor = CosmicTheme.colors.selected,
                disabledDayContentColor = CosmicTheme.colors.bodyGray.copy(alpha = 0.5f),
                dividerColor = CosmicTheme.colors.borderVariant,
                titleContentColor = CosmicTheme.colors.selected,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = CosmicTheme.colors.whitePrimary,
                    unfocusedTextColor = CosmicTheme.colors.whitePrimary,
                    focusedContainerColor = CosmicTheme.colors.surface,
                    unfocusedContainerColor = CosmicTheme.colors.surface,
                    focusedIndicatorColor = CosmicTheme.colors.selected,
                    unfocusedIndicatorColor = CosmicTheme.colors.bodyGray,
                    focusedLabelColor = CosmicTheme.colors.selected,
                    unfocusedLabelColor = CosmicTheme.colors.bodyGray,
                    cursorColor = CosmicTheme.colors.selected,
                )
            ),
        )
    }
}
