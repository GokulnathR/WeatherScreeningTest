package com.screening.knowyourweather.ui.weatherReport


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.screening.knowyourweather.AppController
import com.screening.knowyourweather.R
import com.screening.knowyourweather.utils.ErrorUtils
import com.screening.knowyourweather.weather.AppApi
import com.screening.knowyourweather.generics.Result

class WeatherReportRepository @Inject constructor(val appApi: AppApi) {

    suspend fun fetchWeatherReport(url: String): Result<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val response = appApi.fetchWeatherReport(url)
                if(response.isSuccessful && response.body() != null){
                    return@withContext Result.Success(response.body() as WeatherReportResponse)
                }else{
                    return@withContext Result.Failure(AppController.getInstance().getString(R.string.api_fetch_error))
                }
            } catch (t: Throwable) {
                return@withContext Result.Failure(ErrorUtils.applyError(t))
            }
        }
    }

}