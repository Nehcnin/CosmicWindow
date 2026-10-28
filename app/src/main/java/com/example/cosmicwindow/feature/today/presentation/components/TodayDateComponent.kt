package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.core.ui.theme.CosmicNavTextSelected
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayDateComponent(date: Date){

    val formattedDate = SimpleDateFormat(
        "MMM dd, yyyy",
        Locale.ENGLISH
    ).format(date)

    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(8.dp)
                .background(
                    color = CosmicNavTextSelected,
                    shape = CircleShape
                )
        )
        Text(
            text = "Today in Space • $formattedDate",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun DatePreview(){
    TodayDateComponent(Date(System.currentTimeMillis()))
}