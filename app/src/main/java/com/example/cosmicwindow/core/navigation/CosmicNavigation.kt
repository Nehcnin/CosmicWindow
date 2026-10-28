package com.example.cosmicwindow.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cosmicwindow.feature.today.presentation.TodayScreen

@Composable
fun CosmicNavigation(

){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TodayRoute
    ){
        composable<TodayRoute>(){
            TodayScreen()
        }
    }
}