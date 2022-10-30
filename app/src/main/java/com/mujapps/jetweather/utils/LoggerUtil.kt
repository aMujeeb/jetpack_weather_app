package com.mujapps.jetweather.utils

import android.util.Log

object LoggerUtil {
    fun logMessage(message: String) {
        Log.d(WeatherConstants.APP_LOGGER_KEY, message)
    }
}