package com.screening.knowyourweather.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.screening.knowyourweather.BuildConfig

object AppUtils {

    fun openAppSettings(ctx: Context) {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        ctx.startActivity(intent)
    }
}