package com.screening.knowyourweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.screening.knowyourweather.network.ApiConstants.weatherParams
import com.screening.knowyourweather.ui.weatherReport.WeatherReportRepository
import com.screening.knowyourweather.ui.weatherReport.WeatherReportResponse
import com.screening.knowyourweather.ui.weatherReport.WeatherReportViewModel
import com.screening.knowyourweather.weather.AppApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class WeatherReportViewModelTest {


    lateinit var weatherReportViewModel: WeatherReportViewModel
    lateinit var weatherReportResponse: WeatherReportResponse

    @Mock
    lateinit var appApi: AppApi

    @Mock
    lateinit var weatherReportRepository: WeatherReportRepository

    @Mock
    lateinit var appController: AppController

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        weatherReportViewModel = WeatherReportViewModel(weatherReportRepository, appController)
        weatherReportResponse = WeatherReportResponse()
    }

    @Test
    fun `when fetching weather report status is success`(){
       runBlocking {
           Mockito.`when`(weatherReportRepository.fetchWeatherReport(weatherParams("New York")))
               .thenReturn(com.screening.knowyourweather.generics.Result.Success(WeatherReportResponse()))
           val response = weatherReportRepository.fetchWeatherReport(weatherParams("New York"))
           val weatherReportResponse = response.result as WeatherReportResponse
           Assert.assertEquals( WeatherReportResponse(), weatherReportResponse)
       }
    }

    @Test
    fun `when fetching weather report status is failure`(){
       // Failure test case here
        runBlocking {
            Mockito.`when`(weatherReportRepository.fetchWeatherReport(""))
                .thenReturn(com.screening.knowyourweather.generics.Result.Failure("Error Message"))
            val response = weatherReportRepository.fetchWeatherReport("")
            val weatherReportResponse = response.message as String
            Assert.assertEquals( "Error Message", weatherReportResponse)
        }
    }


}