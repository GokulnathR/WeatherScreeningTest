package com.screening.knowyourweather.ui.weatherReport;


/**
 *
 * @WeatherReportViewState is a Java Implementation
 */
public interface WeatherReportViewState {

    void showLoading();
    void showSuccess(WeatherReportResponse weatherReportResponse);
    void showError(String error);
    void hideLoading();
}
