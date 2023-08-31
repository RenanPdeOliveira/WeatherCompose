package com.example.weathercompose.domain.location

import android.location.Location

interface WeatherLocation {
    suspend fun getCurrentLocation(): Location?
}