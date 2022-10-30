package com.mujapps.jetweather.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mujapps.jetweather.R
import com.mujapps.jetweather.data.DataOrException
import com.mujapps.jetweather.model.CityWeather
import com.mujapps.jetweather.model.WeatherData
import com.mujapps.jetweather.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mMainViewModel: MainViewModel = hiltViewModel()) {

    val weatherData = produceState<DataOrException<CityWeather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mMainViewModel.getWeatherData("moscow")
    }.value
    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        ShowWeatherMainScaffold(weatherData.data!!, navController, mMainViewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowWeatherMainScaffold(
    data: CityWeather,
    navController: NavController,
    mMainViewModel: MainViewModel
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = data.name + "-" + data.sys.country,
            icon = Icons.Default.ArrowBack,
            navController = navController,
            elevation = 4.dp
        ) {
            //Lambda for back arrow click this can be used because final parameter of weather ap bar is an Lambda
        }
    }) {
        MainContent(
            data,
            mMainViewModel
        ) //it gives an error. from 1.2.0 it is need to passed to top composable
    }
}

@Composable
fun MainContent(data: CityWeather, mMainViewModel: MainViewModel) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.weather[0].icon}.png"
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mMainViewModel.getFormattedDate(data.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(4.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = Color(0xfffffc400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = mMainViewModel.getFormattedTemp(data.main.temp) + "º",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = data.weather[0].main, style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
        HumidityPressureRow(data)
        Divider()

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Weather Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityPressureRow(data: CityWeather) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.main.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pressure),
                contentDescription = "Pressure",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.main.pressure}psi", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.wind.speed} Kmph", style = MaterialTheme.typography.caption)
        }
    }
}
