package com.example.cosmicwindow.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cosmicwindow.core.ui.components.CosmicBottomBar
import com.example.cosmicwindow.feature.today.presentation.TodayContent
import com.example.cosmicwindow.feature.today.presentation.TodayScreen

@Composable
fun CosmicNavigation(

){
    val navController = rememberNavController()


    Scaffold(
        bottomBar = { CosmicBottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodayRoute,
            modifier = Modifier.padding(innerPadding)
        ){
            composable<TodayRoute>(){
                TodayScreen()
            }
        }
    }



}