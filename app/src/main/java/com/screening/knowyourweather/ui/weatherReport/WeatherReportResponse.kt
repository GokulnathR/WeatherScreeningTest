package com.screening.knowyourweather.ui.weatherReport

data class WeatherReportResponse(
	val visibility: Int? = null,
	val timezone: Int? = null,
	val main: Main? = null,
	val clouds: Clouds? = null,
	val sys: Sys? = null,
	val dt: Int? = null,
	val coord: Coord? = null,
	val weather: List<WeatherItem?>? = null,
	val name: String? = null,
	val cod: Int? = null,
	val id: Int? = null,
	val base: String? = null,
	val wind: Wind? = null
)

data class Wind(
	val deg: Int? = null,
	val speed: Any? = null,
	val gust: Any? = null
)

data class WeatherItem(
	val icon: String? = null,
	val description: String? = null,
	val main: String? = null,
	val id: Int? = null
)

data class Coord(
	val lon: Any? = null,
	val lat: Any? = null
)

data class Main(
	val temp: String? = null,
	val temp_min: String? = null,
	val humidity: String? = null,
	val pressure: String? = null,
	val feelsLike: String? = null,
	val temp_max: String? = null
)

data class Clouds(
	val all: Int? = null
)

data class Sys(
	val country: String? = null,
	val sunrise: Int? = null,
	val sunset: Int? = null,
	val id: Int? = null,
	val type: Int? = null
)

