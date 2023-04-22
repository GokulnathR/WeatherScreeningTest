package com.screening.knowyourweather.ui.weatherReport;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.screening.knowyourweather.base.LocationBaseActivity;
import com.screening.knowyourweather.databinding.ActivityWeatherReportBinding;
import com.screening.knowyourweather.utils.RxJavaUtils;
import com.screening.knowyourweather.utils.model.LatLngModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 *
 * WeatherReportActivity is written in Java. It extends @{@link LocationBaseActivity} where we handled runtime permission
 * and location updates.
 *
 */
@AndroidEntryPoint
public class WeatherReportActivity extends LocationBaseActivity {
    private ActivityWeatherReportBinding _binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityWeatherReportBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

    }


    @Override
    public void getViewModel() {
    }


    @Override
    protected void onLocationChanged(@NonNull Location location) {
        RxJavaUtils.getInstance().publish(new LatLngModel(location.getLatitude(), location.getLongitude()));
    }

    @Override
    protected void onDestroy() {
        _binding = null;

        super.onDestroy();
    }
}