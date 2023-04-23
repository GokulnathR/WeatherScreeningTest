package com.screening.knowyourweather

import com.screening.knowyourweather.ui.weatherReport.WeatherReportRepository
import com.screening.knowyourweather.ui.weatherReport.WeatherReportResponse
import com.screening.knowyourweather.weather.AppApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doThrow
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class WeatherReportRepositoryTest {


    lateinit var weatherReportRepository: WeatherReportRepository

    @Mock
    lateinit var appApi: AppApi

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherReportRepository = WeatherReportRepository(appApi)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `when weather status repository is success`() {
        runBlocking {
            Mockito.`when`(appApi.fetchWeatherReport("/"))
                .thenReturn(Response.success(WeatherReportResponse()))

            val response = weatherReportRepository.fetchWeatherReport("/")
            assertEquals(WeatherReportResponse(), response.result)
        }
    }

    @Test
    fun `when weather status repository is failure`() {
        runBlocking {

            doThrow(java.lang.IllegalArgumentException("No arguments passed"))
                .`when`(appApi).fetchWeatherReport("")
            val response = weatherReportRepository.fetchWeatherReport("")

            assertEquals(true, response.message.equals("No arguments passed"))

        }
    }

}