package com.screening.knowyourweather.ui.weatherReport;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.screening.knowyourweather.R;
import com.screening.knowyourweather.base.BaseFragment;
import com.screening.knowyourweather.data.AppPreference;
import com.screening.knowyourweather.databinding.LayoutDashboardBinding;
import com.screening.knowyourweather.network.ApiConstants;
import com.screening.knowyourweather.utils.RxJavaUtils;
import com.screening.knowyourweather.utils.model.LatLngModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class DashboardFragment extends BaseFragment implements WeatherReportViewState, View.OnClickListener {

    private LayoutDashboardBinding _binding = null;
    private WeatherReportViewModel weatherReportViewModel = null;

    private NavController navController = null;

    private Location location = null;

    private boolean isFirstTime = true;

    @Inject
    AppPreference appPreference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = LayoutDashboardBinding.inflate(inflater, container, false);
        return _binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstTime)
            fetchWeatherDetails("New York");

        subscribeRxObservers();
        initiateNavigation();
        initClicks();
    }


    private void initClicks() {
        _binding.buttonSearch.setOnClickListener(this);
        _binding.buttonCurrentLocationWeather.setOnClickListener(this);
    }

    @Override
    public void showLoading() {
        _binding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccess(WeatherReportResponse weatherReportResponse) {

        updateUI(weatherReportResponse);
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    @Override
    public void hideLoading() {
        _binding.progress.setVisibility(View.GONE);
    }

    private void initiateNavigation() {
        navController = Navigation.findNavController(requireView());

    }

    @Override
    public void getViewModel() {
        weatherReportViewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);
        weatherReportViewModel.attachView(this, getLifecycle());
    }

    private void fetchWeatherDetails(String city) {
        isFirstTime = false;
        weatherReportViewModel.fetchWeatherReport(ApiConstants.INSTANCE.weatherParams(city));
    }

    private void fetchWeatherDetailsByLatLng(String lat, String lng) {
        weatherReportViewModel.fetchWeatherReport(ApiConstants.INSTANCE.weatherParamsWithLatLng(lat, lng));
    }

    private void subscribeRxObservers() {

        // Observe City from SearchFragment
        RxJavaUtils.getInstance().listen().subscribe(cityOberver());

        // Observe Location from WeatherReportActivity
        RxJavaUtils.getInstance().listenLocation().subscribe(locationOberver());
    }

    private Observer<String> cityOberver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String cityName) {

                fetchWeatherDetails(cityName);

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observer<LatLngModel> locationOberver() {
        return new Observer<LatLngModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(LatLngModel latLng) {

                setLocation(latLng);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void setLocation(LatLngModel latLng) {
        if (location == null) {
            location = new Location("");
            location.setLatitude(latLng.getLat());
            location.setLongitude(latLng.getLng());

            fetchWeatherDetailsByLatLng(String.valueOf(latLng.getLat()), String.valueOf(latLng.getLng()));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSearch: {
                navController.navigate(R.id.action_nav_search);
            }
            case R.id.buttonCurrentLocationWeather: {
                enableLocationPermission();
                appPreference.setKEY_UPDATE_CURRENT_LOC(true);
            }

        }
    }

    private void updateUI(WeatherReportResponse weatherReportResponse) {
        _binding.idCityName.setText(weatherReportResponse.getName());
        _binding.idTemperature.setText(weatherReportResponse.getMain().getTemp() + "\u2103");
        _binding.idTemperatureDesc.setText(weatherReportResponse.getWeather().get(0).getDescription());
        _binding.valueMinTemp.setText(weatherReportResponse.getMain().getTemp_min() + "\u2103");
        _binding.valueMaxTemp.setText(weatherReportResponse.getMain().getTemp_max() + "\u2103");
        _binding.valueHumidity.setText(weatherReportResponse.getMain().getHumidity());
        _binding.valuePressure.setText(weatherReportResponse.getMain().getPressure());
        Glide.with(requireContext()).load(ApiConstants.INSTANCE.loadImageUrl(weatherReportResponse.getWeather().get(0).getIcon())).into(_binding.imgWeatherIndicator);

        // Hiding inner layouts until api loading and visible after loading complete.
        _binding.idTemperaturesLayout.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onLocationChanged(@NonNull Location location) {
        //This will be called only after the user clicks the my location button
        // after that it will be called periodically(60 seconds) while the user is in moving.

        if (appPreference.getKEY_UPDATE_CURRENT_LOC()) //Conditioning based on the user selection (My Location or Search Location).
            fetchWeatherDetailsByLatLng(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
    }

}
