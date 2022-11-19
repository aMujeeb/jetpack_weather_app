package com.mujapps.jetweather.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.jetweather.data.DataOrException
import com.mujapps.jetweather.model.CityWeather
import com.mujapps.jetweather.repository.WeatherRepository
import com.mujapps.jetweather.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mWeatherRepo: WeatherRepository) : ViewModel() {

    private val mSimpleDateFormatter = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
    private val mSimpleTimeFormatter = SimpleDateFormat("hh:mm:aa", Locale.getDefault())
    /* val data: MutableState<DataOrException<CityWeather, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

   init {
        loadCityWeatherData()
    }

    private fun loadCityWeatherData() {
        getWeatherData("moscow")
    }

    private fun getWeatherData(city: String) {
        viewModelScope.launch {
            if(city.isEmpty()) return@launch
            data.value.loading = true
            data.value = mWeatherRepo.getCityBaseWeather(cityName = city)
            if(data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        LoggerUtil.logMessage(data.value.data.toString())
    }
    */

    suspend fun getWeatherData(
        city: String,
        unit: String
    ): DataOrException<CityWeather, Boolean, Exception> {
        return mWeatherRepo.getCityBaseWeather(city, unit)
    }

    fun getFormattedDate(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time.time = millis
        return mSimpleDateFormatter.format(calendar.time)
    }

    fun getFormattedTime(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time.time = millis * 1000
        return mSimpleTimeFormatter.format(calendar.time)
    }

    fun getFormattedTemp(value: Double) = String.format("%.0f", value)
}