package com.screening.knowyourweather.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.screening.knowyourweather.utils.LoggerUtils

/**
 * This is magic live data.
 *
 * It auto starts tracking location if there is any of active observers [androidx.lifecycle.LiveData.onActive].
 * Also it auto stops tracking if there is no observers [androidx.lifecycle.LiveData.onInactive].
 *
 * Create static object of this & put inside App class (or any other class),
 * then wee can safely observe this live data from any other UI components.
 *
 * @param context Should be App context
 */
class LocationLiveData(context: Context) : LiveData<LocationModel>() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
                setLocationData(it)
            }
        }
    }

    private fun setLocationData(location: Location) {
        LoggerUtils.d("LocationLiveData","lat -> ${location.latitude} long -> ${location.longitude}")

        value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    companion object {
        private const val INTERVAL = (10000 * 6).toLong() // 60 seconds
        private const val FAST_INTERVAL = INTERVAL
        val locationRequest: LocationRequest =
            LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, INTERVAL).apply {
                setMinUpdateIntervalMillis(FAST_INTERVAL)
                setMinUpdateDistanceMeters(100f) // 100 meters
            }.build()
    }
}

data class LocationModel(
    val longitude: Double,
    val latitude: Double
)
