package com.mujapps.jetweather.screens.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.jetweather.model.Favourite
import com.mujapps.jetweather.navigation.WeatherScreens
import com.mujapps.jetweather.widgets.WeatherAppBar

@Composable
fun FavouriteScreen(
    navController: NavController,
    mFavouriteViewModel: FavouriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Favourite",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {
        it.calculateBottomPadding()
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = mFavouriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(items = list) { item ->
                        CityRow(item, navController = navController, mFavouriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favItem: Favourite,
    navController: NavController,
    mFavouriteViewModel: FavouriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                navController.navigate(
                    WeatherScreens.MainScreen.name + "/${
                        favItem.city
                            .substringBefore(
                                "-"
                            )
                            .trim()
                    }"
                )
            },
        shape = CircleShape.copy(topEnd = CornerSize(8.dp)), color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favItem.city, modifier = Modifier.padding(start = 8.dp))
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favItem.country,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete record",
                modifier = Modifier.clickable {
                    mFavouriteViewModel.deleteFavouriteCity(favItem)
                }, tint = Color.Magenta
            )
        }
    }
}
