package com.mujapps.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mujapps.jetweather.screens.main.MainScreen
import com.mujapps.jetweather.screens.WeatherSplashScreen
import com.mujapps.jetweather.screens.main.MainViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name){
            val mMainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mMainViewModel)
        }
    }
}