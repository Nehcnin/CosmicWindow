package com.example.cosmicwindow.feature.today.presentation.components

import android.R.attr.y
import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import java.time.LocalDate

@Composable
fun TodayHeader(viewModel: TodayViewModel) {

    val context = LocalContext.current
    val selectedDate = viewModel.date
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_header_today),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Column(
            Modifier.offset(y = (-4).dp)
        ) {
            Text(
                text = "Cosmic Window",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "ASTRONOMY PICTURE OF THE DAY",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            DatePickerDialog(
                context,
                {_, year, month, day ->
                    viewModel.updateDate(LocalDate.of(year, month+1, day))
                },
                selectedDate.year,
                selectedDate.monthValue -1,
                selectedDate.dayOfMonth
            ).apply {
                datePicker.maxDate = System.currentTimeMillis()
            }.show()
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_header_calendar),
                contentDescription = "Calendar",
                modifier = Modifier.size(24.dp),
                tint = CosmicIconGray
            )
        }


    }
}