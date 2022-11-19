package com.mujapps.jetweather.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.jetweather.model.MeasurementUnit
import com.mujapps.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val mRepository: WeatherDbRepository) :
    ViewModel() {

    private val _unitList = MutableStateFlow<List<MeasurementUnit>>(emptyList())
    val mUnitsList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getMeasurementUnits().distinctUntilChanged().collect { unitsList ->
                if (unitsList.isNullOrEmpty()) {

                } else {
                    _unitList.value = unitsList
                }
            }
        }
    }

    fun insertMeasurementUnit(unit: MeasurementUnit) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.insertMeasurementUnit(unit)
        }

    fun updateMeasurementUnit(unit: MeasurementUnit) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.updateMeasurementUnit(unit)
        }

    fun deleteMeasurementUnit(unit: MeasurementUnit) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteMeasurementUnit(unit)
        }

    fun deleteAllMeasurements() =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteAllMeasurementUnits()
        }
}