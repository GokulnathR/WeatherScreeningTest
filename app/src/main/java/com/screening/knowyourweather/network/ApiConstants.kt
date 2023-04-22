package com.screening.knowyourweather.network

import com.screening.knowyourweather.BuildConfig


object ApiConstants {


    fun weatherParams(city: String): String = "weather?q=${city}&appid=${BuildConfig.API_KEY}&units=metric"

    fun weatherParamsWithLatLng(lat: String, lng: String): String = "weather?lat=${lat}&lon=${lng}&appid=${BuildConfig.API_KEY}&units=metric"

    fun loadImageUrl(imageName: String): String = "https://openweathermap.org/img/wn/${imageName}.png"
}