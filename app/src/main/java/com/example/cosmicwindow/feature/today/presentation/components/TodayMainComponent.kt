package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.Date

@Composable
fun TodayMainComponent(){

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        TodayDateComponent(Date(System.currentTimeMillis()))
    }
}