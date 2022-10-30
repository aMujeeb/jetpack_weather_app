package com.mujapps.jetweather.model

data class WeatherData(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)