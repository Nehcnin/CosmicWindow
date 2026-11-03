package com.example.cosmicwindow.core.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.cosmicwindow.feature.detail.presentation.DetailScreen
import com.example.cosmicwindow.feature.today.presentation.TodayContent
import com.example.cosmicwindow.feature.today.presentation.TodayScreen
import com.example.cosmicwindow.feature.today.presentation.TodayViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CosmicNavigation(

){
    val navController = rememberNavController()
    val todayViewModel: TodayViewModel = koinViewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val showBottomBar = destination?.hasRoute<DetailRoute>() == false

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CosmicBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodayRoute,
            modifier = Modifier.padding(innerPadding)
        ){
            composable<TodayRoute>(){
                TodayScreen(
                    viewModel = todayViewModel,
                    onReadStory = {date ->
                        Log.d("NAV_DEBUG", "Navigating with date: $date")
                        navController.navigate(DetailRoute(date))
                    }
                )
            }


            composable<DetailRoute> { entry ->
                val route = entry.toRoute<DetailRoute>()

                DetailScreen(
                    date = route.date,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }



}