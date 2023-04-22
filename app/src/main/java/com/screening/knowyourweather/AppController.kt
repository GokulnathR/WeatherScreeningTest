package com.screening.knowyourweather

import android.app.Application
import com.screening.knowyourweather.location.LocationLiveData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController: Application() {

    override fun onCreate() {
        super.onCreate()
        appController = this
        locationLiveData = LocationLiveData(this)

    }

    companion object{
        private var appController: AppController? = null
        lateinit var locationLiveData: LocationLiveData
        @Synchronized
        fun getInstance(): AppController{
            return appController!!
        }

    }
}