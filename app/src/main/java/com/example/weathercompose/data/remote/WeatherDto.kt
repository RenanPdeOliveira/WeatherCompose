package com.example.weathercompose.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("hourly")
    val weatherData: WeatherDataDto
)