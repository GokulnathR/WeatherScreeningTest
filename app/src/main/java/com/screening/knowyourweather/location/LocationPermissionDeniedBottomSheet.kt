package com.screening.knowyourweather.location


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.screening.knowyourweather.databinding.FragmentLocationPermissionDeniedBottomsheetBinding
import com.screening.knowyourweather.utils.AppUtils.openAppSettings

/**
 *
 * This it to tell the user why the location permission is required and
 * Also provided close button to dismiss the bottom sheet if they dont want to give permission.
 */

class LocationPermissionDeniedBottomSheet() :
    BottomSheetDialogFragment() {
    private var _binding: FragmentLocationPermissionDeniedBottomsheetBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentLocationPermissionDeniedBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idIcClose.visibility = VISIBLE
        binding.idIcClose.setOnClickListener {
            dismiss()
        }


        binding.idBtnSettings.setOnClickListener {
            openAppSettings(it.context)

            dismiss()

        }

    }

}

