package com.screening.knowyourweather

import android.graphics.Movie
import com.screening.knowyourweather.ui.weatherReport.WeatherReportRepository
import com.screening.knowyourweather.ui.weatherReport.WeatherReportResponse
import com.screening.knowyourweather.weather.AppApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class WeatherReportRepositoryTest {


    lateinit var weatherReportRepository: WeatherReportRepository

    @Mock
    lateinit var appApi: AppApi


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherReportRepository = WeatherReportRepository(appApi)
    }

    @Test
    fun `when weather status repository is success`() {
        runBlocking {
            Mockito.`when`(appApi.fetchWeatherReport("/"))
                .thenReturn(Response.success(WeatherReportResponse()))

            val response = appApi.fetchWeatherReport("/")
            assertEquals(WeatherReportResponse(), response.body())
        }
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun `when weather status repository is failure`() {
        runBlocking {
            Mockito.`when`(appApi.fetchWeatherReport(""))
                .thenThrow(java.lang.IllegalArgumentException::class.java)

            Mockito.verify(appApi).fetchWeatherReport("")
        }
    }

}