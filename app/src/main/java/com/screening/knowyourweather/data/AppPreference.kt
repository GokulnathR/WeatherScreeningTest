package com.screening.knowyourweather.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreference @Inject constructor(context: Context) {

    private val preference: SharedPreferences

    companion object {
        private val PREFERENCE_NAME = "WEATHER_PREFERENCE"

        private val _KEY_CITY = "_KEY_CITY"
        private val _KEY_LATITUDE = "_KEY_LATITUDE"
        private val _KEY_LONGITUDE = "_KEY_LONGITUDE"
        private val _KEY_UPDATE_CURRENT_LOC = "_KEY_UPDATE_CURRENT_LOC"


    }

    init {
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }


    /*---------------------------------------------------------Clear Preference -----------------------------------------------------------*/
    fun clearAppPreference() {
        preference.edit().clear().apply()
    }

    /*----------------------------------------------------------Login User Data-------------------------------------------------------------*/
    var KEY_CITY: String
        set(value) = preference.edit().putString(_KEY_CITY, value).apply()
        get() = preference.getString(_KEY_CITY, "")!!

    var KEY_LATITUDE: String
        set(value) = preference.edit().putString(_KEY_LATITUDE, value).apply()
        get() = preference.getString(_KEY_LATITUDE, "")!!

    var KEY_LONGITUDE: String
        set(value) = preference.edit().putString(_KEY_LONGITUDE, value).apply()
        get() = preference.getString(_KEY_LONGITUDE, "")!!

    var KEY_UPDATE_CURRENT_LOC: Boolean
        set(value) = preference.edit().putBoolean(_KEY_UPDATE_CURRENT_LOC, value).apply()
        get() = preference.getBoolean(_KEY_UPDATE_CURRENT_LOC, true)
}