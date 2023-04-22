package com.screening.knowyourweather.ui.weatherReport

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.screening.knowyourweather.weather.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.screening.knowyourweather.generics.Result

@HiltViewModel
class WeatherReportViewModel @Inject constructor(val weatherReportRepository: WeatherReportRepository, app: Application) :
    BaseViewModel<WeatherReportViewState>(app) {

    fun fetchWeatherReport(url: String){
        viewModelScope.launch {
            try {
                viewState()?.showLoading()

                val result = weatherReportRepository.fetchWeatherReport(url)
                when(result) {
                    is Result.Success
                    -> {
                        viewState()?.showSuccess(result.result as WeatherReportResponse)
                    }
                    is Result.Failure -> viewState()?.showError(result.message)
                }
            } finally {
                viewState()?.hideLoading()
            }
        }
    }
}