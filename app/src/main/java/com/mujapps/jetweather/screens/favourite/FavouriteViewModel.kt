package com.mujapps.jetweather.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.jetweather.model.Favourite
import com.mujapps.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val mRepository: WeatherDbRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getFavorites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    // No data scenario. Handle with a message popup or any other indicator
                } else {
                    _favList.value = listOfFavs
                }
            }
        }
    }

    fun insertFavouriteCity(favourite: Favourite) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.insertFavourite(favourite)
        }

    fun updateFavouriteCity(favourite: Favourite) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.updateFavourite(favourite)
        }

    fun deleteFavouriteCity(favourite: Favourite) =
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.deleteFavourite(favourite)
        }
}