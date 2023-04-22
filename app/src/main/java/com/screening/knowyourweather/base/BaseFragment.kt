package com.screening.knowyourweather.base

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.screening.knowyourweather.AppController
import com.screening.knowyourweather.location.LocationLiveData
import com.screening.knowyourweather.location.LocationPermissionDeniedBottomSheet

abstract class BaseFragment() : Fragment() {

    private var locationPermissionDeniedBottomSheet: LocationPermissionDeniedBottomSheet? = null

    protected abstract fun onLocationChanged(location: Location)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel()
    }

    abstract fun getViewModel()

    protected fun showToast(input: String) {
        Toast.makeText(requireContext(), input, Toast.LENGTH_SHORT).show()
    }

    protected fun enableLocationPermission(){
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    observeLocation()
                    AppController.locationLiveData = LocationLiveData(AppController.getInstance())
                    locationPermissionDeniedBottomSheet?.dismiss()
                    locationPermissionDeniedBottomSheet = null

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    locationPermissionDeniedBottomSheet?.dismiss()
                    locationPermissionDeniedBottomSheet =
                        LocationPermissionDeniedBottomSheet()
                    locationPermissionDeniedBottomSheet?.show(childFragmentManager, null)
                }

            })
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
    }

    private fun observeLocation() {
        AppController.locationLiveData.removeObservers(this) // To avoid duplicate observers
        AppController.locationLiveData.observe(this) {
            onLocationChanged(Location("").apply {
                latitude = it.latitude
                longitude = it.longitude

            })
        }
    }
}