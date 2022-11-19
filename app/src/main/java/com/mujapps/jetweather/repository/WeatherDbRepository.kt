package com.mujapps.jetweather.repository

import com.mujapps.jetweather.data.MeasurementUnitDao
import com.mujapps.jetweather.data.WeatherDao
import com.mujapps.jetweather.model.Favourite
import com.mujapps.jetweather.model.MeasurementUnit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val mWeatherDao: WeatherDao,
    private val mMeasurementUnitDao: MeasurementUnitDao
) {
    fun getFavorites(): Flow<List<Favourite>> = mWeatherDao.getFavourites()
    suspend fun insertFavourite(favourite: Favourite) = mWeatherDao.insertFavouriteCity(favourite)
    suspend fun updateFavourite(favourite: Favourite) = mWeatherDao.updateFavourite(favourite)
    suspend fun deleteAllFavourite() = mWeatherDao.deleteAllRecords()
    suspend fun deleteFavourite(favourite: Favourite) =
        mWeatherDao.deleteFavouriteCityRecord(favourite)

    suspend fun getFavouriteByCityName(city: String) = mWeatherDao.getFavById(city)

    fun getMeasurementUnits(): Flow<List<MeasurementUnit>> = mMeasurementUnitDao.getUnits()
    suspend fun insertMeasurementUnit(unit: MeasurementUnit) = mMeasurementUnitDao.insertUnit(unit)
    suspend fun updateMeasurementUnit(unit: MeasurementUnit) = mMeasurementUnitDao.updateUnit(unit)
    suspend fun deleteAllMeasurementUnits() = mMeasurementUnitDao.deleteAllRecords()
    suspend fun deleteMeasurementUnit(unit: MeasurementUnit) =
        mMeasurementUnitDao.deleteUnitMeasurementRecord(unit)
}