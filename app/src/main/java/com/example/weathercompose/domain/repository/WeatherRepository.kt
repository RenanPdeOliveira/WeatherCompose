package com.example.weathercompose.domain.repository

import com.example.weathercompose.domain.util.Resource
import com.example.weathercompose.domain.weather.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}