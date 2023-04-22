package com.screening.knowyourweather

import com.google.gson.Gson
import com.screening.knowyourweather.ui.weatherReport.WeatherReportResponse
import com.screening.knowyourweather.weather.AppApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
class AppApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var appApi: AppApi
    lateinit var gson: Gson


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        gson = Gson()
        appApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(AppApi::class.java)
    }

    @Test
    fun `weather report api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody(gson.toJson(WeatherReportResponse())))
            val response = appApi.fetchWeatherReport("")
            val request = mockWebServer.takeRequest()
            assertEquals("/",request.path)
            assertEquals(gson.toJson(WeatherReportResponse()), gson.toJson(response.body()))
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}