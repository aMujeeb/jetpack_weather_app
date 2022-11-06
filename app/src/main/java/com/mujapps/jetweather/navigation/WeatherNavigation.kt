package com.mujapps.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mujapps.jetweather.screens.about.AboutScreen
import com.mujapps.jetweather.screens.favourite.FavouriteScreen
import com.mujapps.jetweather.screens.main.MainScreen
import com.mujapps.jetweather.screens.splash.WeatherSplashScreen
import com.mujapps.jetweather.screens.main.MainViewModel
import com.mujapps.jetweather.screens.search.SearchScreen
import com.mujapps.jetweather.screens.settings.SettingsScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        //City is the variable passed in to main screen url kind format eg:www.weather.com/cityName="colombo"
        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(
                //name should be exact name of variable
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { cityName ->
                val mMainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mMainViewModel, cityName)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController = navController)
        }
    }
}