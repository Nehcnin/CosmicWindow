package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.ui.theme.CosmicIconGray

@Composable
fun CosmicBottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 12.dp)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CosmicBottomBarElement(
            text = "Today",
            description = "nav_today",
            icon = R.drawable.ic_nav_today
        )

        CosmicBottomBarElement(
            text = "Earth",
            description = "nav_earth",
            icon = R.drawable.ic_nav_earth
        )

        CosmicBottomBarElement(
            text = "Explore",
            description = "nav_explore",
            icon = R.drawable.ic_nav_explore,
            selected = true
        )

        CosmicBottomBarElement(
            text = "Saved",
            description = "nav_saved",
            icon = R.drawable.ic_nav_saved,
            selected = true
        )


    }
}

@Composable
@Preview
fun CosmicBottomBarPreview(){
    CosmicBottomBar()
}

