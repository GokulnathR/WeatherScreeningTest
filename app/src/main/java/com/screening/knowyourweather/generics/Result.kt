package com.screening.knowyourweather.generics

/**
 *
 * @Result class makes you comfortable on handling Api responses for both
 * success and failure cases.
 *
 */

sealed class Result<T>(val result: T? = null, val message: String?=null){
     class Success<T>(result: T) : Result<T>(result)
     class Failure<T>(message: String?): Result<T>(message = message)
}
