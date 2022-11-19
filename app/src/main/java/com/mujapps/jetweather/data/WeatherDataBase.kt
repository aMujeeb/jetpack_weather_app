package com.mujapps.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mujapps.jetweather.model.Favourite
import com.mujapps.jetweather.model.MeasurementUnit

@Database(entities = [Favourite::class, MeasurementUnit::class], version = 2, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun measurementUnitsDao(): MeasurementUnitDao
}