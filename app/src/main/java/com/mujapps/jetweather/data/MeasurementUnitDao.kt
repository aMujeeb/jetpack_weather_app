package com.mujapps.jetweather.data

import androidx.room.*
import com.mujapps.jetweather.model.MeasurementUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementUnitDao {

    @Query("SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<MeasurementUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: MeasurementUnit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: MeasurementUnit)

    @Query("DELETE FROM settings_tbl")
    suspend fun deleteAllRecords()

    @Delete
    suspend fun deleteUnitMeasurementRecord(unit: MeasurementUnit)
}