package com.mujapps.jetweather.repository

import com.mujapps.jetweather.data.WeatherDao
import com.mujapps.jetweather.model.Favourite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val mWeatherDao: WeatherDao
) {
    fun getFavorites(): Flow<List<Favourite>> = mWeatherDao.getFavourites()
    suspend fun insertFavourite(favourite: Favourite) = mWeatherDao.insertFavouriteCity(favourite)
    suspend fun updateFavourite(favourite: Favourite) = mWeatherDao.updateFavourite(favourite)
    suspend fun deleteAllFavourite() = mWeatherDao.deleteAllRecords()
    suspend fun deleteFavourite(favourite: Favourite) =
        mWeatherDao.deleteFavouriteCityRecord(favourite)

    suspend fun getFavouriteByCityName(city: String) = mWeatherDao.getFavById(city)
}