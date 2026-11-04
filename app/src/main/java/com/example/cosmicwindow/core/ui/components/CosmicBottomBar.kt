package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.navigation.EarthRoute
import com.example.cosmicwindow.core.navigation.TodayRoute
import java.time.LocalDate

@Composable
fun CosmicBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectedDate: LocalDate,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntry?.destination

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 12.dp)
                .height(80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CosmicBottomBarElement(
            text = "Today",
            description = "nav_today",
            icon = R.drawable.ic_nav_today,
            selected = destination?.hasRoute<TodayRoute>() == true,
            onClick = { navController.navigate(TodayRoute) },
        )

        CosmicBottomBarElement(
            text = "Earth",
            description = "nav_earth",
            icon = R.drawable.ic_nav_earth,
            selected = destination?.hasRoute<EarthRoute>() == true,
            onClick = { navController.navigate(EarthRoute(selectedDate.toString())) },
        )

        CosmicBottomBarElement(
            text = "Explore",
            description = "nav_explore",
            icon = R.drawable.ic_nav_explore,
            onClick = {},
        )

        CosmicBottomBarElement(
            text = "Saved",
            description = "nav_saved",
            icon = R.drawable.ic_nav_saved,
            onClick = {},
        )
    }
}
