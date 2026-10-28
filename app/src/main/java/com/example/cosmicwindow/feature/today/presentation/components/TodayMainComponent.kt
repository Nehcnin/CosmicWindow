package com.example.cosmicwindow.feature.today.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cosmicwindow.feature.today.domain.model.Apod
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import java.util.Date

@Composable
fun TodayMainComponent(viewModel: TodayViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TodayDateComponent(Date(System.currentTimeMillis()))
        val apod: Apod? = viewModel.apod

        if (apod?.imgUrl != null) {
            AsyncImage(
                model = apod.imgUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                apod.title
            )
        }

    }
}