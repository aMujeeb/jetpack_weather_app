package com.mujapps.jetweather.network

import com.mujapps.jetweather.model.CityWeather
import com.mujapps.jetweather.utils.WeatherConstants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    //https://api.openweathermap.org/data/2.5/weather?q=moscow&appid=4bdcf527ae91c7e4e0079d4cff2ffeae
    @GET("weather")
    suspend fun getWeatherCityBaseData(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("appid") apiId: String = WeatherConstants.API_KEY
    ): CityWeather
}