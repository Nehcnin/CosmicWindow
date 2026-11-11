package com.example.cosmicwindow.core.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.cosmicwindow.core.ui.components.CosmicBottomBar
import com.example.cosmicwindow.core.ui.theme.CosmicTheme
import com.example.cosmicwindow.feature.detail.presentation.DetailScreen
import com.example.cosmicwindow.feature.earth.presentation.EarthScreen
import com.example.cosmicwindow.feature.earth.presentation.EarthViewModel
import com.example.cosmicwindow.feature.explore.presentation.ExploreScreen
import com.example.cosmicwindow.feature.explore.presentation.ExploreViewModel
import com.example.cosmicwindow.feature.today.presentation.TodayScreen
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CosmicNavigation() {
    val navController = rememberNavController()
    val todayViewModel: TodayViewModel = koinViewModel()

    val earthViewModel: EarthViewModel = koinViewModel()
    val exploreViewModel: ExploreViewModel = koinViewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val showBottomBar = destination?.hasRoute<DetailRoute>() == false

    Scaffold(
        containerColor = CosmicTheme.colors.background,
        bottomBar = {
            if (showBottomBar) {
                CosmicBottomBar(
                    navController = navController,
                    selectedDate = todayViewModel.date,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodayRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<TodayRoute> {
                TodayScreen(
                    viewModel = todayViewModel,
                    onReadStory = { date ->
                        Log.d("NAV_DEBUG", "Navigating with date: $date")
                        navController.navigate(DetailRoute(date))
                    },
                )
            }

            composable<EarthRoute> { entry ->
                val route = entry.toRoute<EarthRoute>()
                EarthScreen(
                    date = route.date,
                    viewModel = earthViewModel,
                )
            }

            composable<DetailRoute> { entry ->
                val route = entry.toRoute<DetailRoute>()

                DetailScreen(
                    date = route.date,
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable<ExploreRoute>{ entry ->
                ExploreScreen(
                    viewModel = exploreViewModel
                )
            }
        }
    }
}
