package com.screening.knowyourweather.weather

import com.screening.knowyourweather.ui.weatherReport.WeatherReportResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface AppApi {

    @GET()
    suspend fun fetchWeatherReport(@Url url: String): Response<WeatherReportResponse>

}