package com.mujapps.jetweather.repository

import com.mujapps.jetweather.data.DataOrException
import com.mujapps.jetweather.model.CityWeather
import com.mujapps.jetweather.network.WeatherApi
import com.mujapps.jetweather.utils.LoggerUtil
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val mApi: WeatherApi) {
    suspend fun getCityBaseWeather(cityName: String, unit : String): DataOrException<CityWeather, Boolean, Exception> {
        val response = try {
            mApi.getWeatherCityBaseData(cityName, units = unit)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}
