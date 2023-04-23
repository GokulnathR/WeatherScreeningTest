package com.screening.knowyourweather.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val cityRepository: CityRepository) : ViewModel(){

    private val _cities = MutableStateFlow(cityRepository.allCityList())
    val cities = _cities.asStateFlow()


}