package com.example.weathercompose.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentlyWeatherData: WeatherData?
)