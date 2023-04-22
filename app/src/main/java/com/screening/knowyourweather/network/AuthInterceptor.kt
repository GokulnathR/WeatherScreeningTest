package com.screening.knowyourweather.network

import com.screening.knowyourweather.data.AppPreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var appPreference: AppPreference


    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorisedRequestBuilder = originalRequest.newBuilder()
            .header("Content-Type", "application/json")

        val response =
            chain.proceed(authorisedRequestBuilder.build())
        return response
    }
}