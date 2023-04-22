package com.screening.knowyourweather.ui.search

import androidx.compose.ui.input.key.Key.Companion.U
import javax.inject.Inject

class CityRepository @Inject constructor() {

    fun allCityList():ArrayList<USCities>{

       return arrayListOf(USCities("New York", "40.697", "-74.259"),
           USCities("New Jersey", "40.046", "-77.224"),
           USCities("California", "37.151", "-124.594"),
           USCities("Texas", "31.060", ",-105.367"),
           USCities("San Francisco", "37.757", "-122.520"),
           USCities("Oakland", "37.758", "-122.400"))
    }
}