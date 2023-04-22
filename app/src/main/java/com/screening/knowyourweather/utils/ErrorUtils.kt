package com.screening.knowyourweather.utils

import com.google.gson.JsonSyntaxException
import com.screening.knowyourweather.AppController
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtils {

    /*Exceptions*/
    private val EXCEPTION_NO_NETWORK_CONNECTION = "No Internet connection"
    private val EXCEPTION_REQUEST_TIMEOUT = "Request timed out"
    private val EXCEPTION_PLEASE_TRY_AGAIN =
        "Something went wrong, please try again after some time."
    private val API_UNKNOWN_FAILURE_MSG = "Something went wrong, Please contact administrator"

//if (!NetworkUtils.isConnected(AppController.getInstance())) {
//            EXCEPTION_NO_NETWORK_CONNECTION
//        } else
    fun applyError(throwable: Throwable): String? {
        return if (throwable is UnknownHostException) {
            EXCEPTION_REQUEST_TIMEOUT
        } else if (throwable is SocketTimeoutException) { // Slow Internet Connection
            EXCEPTION_REQUEST_TIMEOUT
        } else if (throwable is JsonSyntaxException) {
            throwable.message
        } else if (throwable is HttpException) {
            return throwable.response()?.errorBody()?.string() ?: EXCEPTION_PLEASE_TRY_AGAIN
        } else {
            return throwable.message ?: EXCEPTION_PLEASE_TRY_AGAIN
        }
    }

}