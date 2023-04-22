package com.screening.knowyourweather.base

import android.Manifest
import android.location.Location
import android.os.Bundle
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.screening.knowyourweather.AppController
import com.screening.knowyourweather.AppController.Companion.locationLiveData
import com.screening.knowyourweather.location.LocationLiveData
import com.screening.knowyourweather.location.LocationPermissionDeniedBottomSheet
import com.screening.knowyourweather.utils.PermissionUtils.hasPermissions

/**
 *
 * @LocationBaseActivity manages the runtime permission for location and observes the
 * current lattitude, longitude values from @{@link LocationLiveData}
 *
 */

abstract class LocationBaseActivity : BaseActivity() {

    private var locationPermissionDeniedBottomSheet: LocationPermissionDeniedBottomSheet? = null

    protected abstract fun onLocationChanged(location: Location)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    observeLocation()
                    locationLiveData = LocationLiveData(AppController.getInstance())
                    locationPermissionDeniedBottomSheet?.dismiss()
                    locationPermissionDeniedBottomSheet = null

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    locationPermissionDeniedBottomSheet?.dismiss()
                    locationPermissionDeniedBottomSheet =
                        LocationPermissionDeniedBottomSheet()
                    locationPermissionDeniedBottomSheet?.show(supportFragmentManager, null)
                }

            })
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()

    }

    override fun onResume() {
        super.onResume()
       if (hasPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )) {
            observeLocation()
            locationPermissionDeniedBottomSheet?.dismiss()
            locationPermissionDeniedBottomSheet = null // Remove reference completely
        }
    }

    private fun observeLocation() {
        locationLiveData.removeObservers(this@LocationBaseActivity) // To avoid duplicate observers
        locationLiveData.observe(this) {
            onLocationChanged(Location("").apply {
                latitude = it.latitude
                longitude = it.longitude

               // showToast("Latitude: ${latitude}, - Longitude: ${longitude}")
            })
        }
    }


}