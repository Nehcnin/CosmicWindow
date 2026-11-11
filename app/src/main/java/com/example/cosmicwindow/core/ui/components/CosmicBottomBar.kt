package com.example.cosmicwindow.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cosmicwindow.R
import com.example.cosmicwindow.core.navigation.EarthRoute
import com.example.cosmicwindow.core.navigation.ExploreRoute
import com.example.cosmicwindow.core.navigation.TodayRoute
import com.example.cosmicwindow.core.ui.theme.CosmicSurfaceBorder
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
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
                .background(CosmicTheme.colors.background)
                .padding(bottom = 12.dp)
                .height(80.dp)
                .drawBehind {
                    drawLine(
                        color = CosmicSurfaceBorder,
                        start = Offset(0f, 0.5f),
                        end = Offset(size.width, 0.5f),
                        strokeWidth = 1.dp.toPx(),
                    )
                },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CosmicBottomBarElement(
            text = "Today",
            description = "nav_today",
            icon = R.drawable.ic_nav_today,
            selected = destination?.hasRoute<TodayRoute>() == true,
            onClick = {
                navController.navigate(TodayRoute){
                    popUpTo(TodayRoute){saveState = true}
                    launchSingleTop = true
                    restoreState = true
                } },
        )

        CosmicBottomBarElement(
            text = "Earth",
            description = "nav_earth",
            icon = R.drawable.ic_nav_earth,
            selected = destination?.hasRoute<EarthRoute>() == true,
            onClick = {
                navController.navigate(EarthRoute(selectedDate.toString())){
                    popUpTo(TodayRoute){saveState = true}
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )

        CosmicBottomBarElement(
            text = "Explore",
            description = "nav_explore",
            icon = R.drawable.ic_nav_explore,
            selected = destination?.hasRoute<ExploreRoute>() == true,
            onClick = {
                navController.navigate(ExploreRoute){
                    popUpTo(TodayRoute){saveState = true}
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )

        CosmicBottomBarElement(
            text = "Saved",
            description = "nav_saved",
            icon = R.drawable.ic_nav_saved,
            onClick = {},
        )
    }
}
