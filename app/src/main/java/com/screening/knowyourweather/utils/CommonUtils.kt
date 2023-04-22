package com.screening.knowyourweather.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import java.util.*

object CommonUtils {

    fun getAddress(ctx: Context, lat:Double, lng: Double):String{
        var city = ""
        val geocoder = Geocoder(ctx, Locale("IN"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, lng, 1,object : Geocoder.GeocodeListener{
                override fun onGeocode(addresses: MutableList<Address>) {
                    city = addresses[0].adminArea
                }
                override fun onError(errorMessage: String?) {
                    super.onError(errorMessage)
                    LoggerUtils.d("Error", errorMessage?:"Error")
                }

            })

        }else{
            try {
               city = geocoder.getFromLocation(lat, lng, 1)?.get(0)?.adminArea?:""
            } catch(e: Exception) {

            }
        }

       return city
    }
}