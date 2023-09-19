package com.example.weathercompose.data.repository

import com.example.weathercompose.data.mappers.toWeatherInfo
import com.example.weathercompose.data.remote.WeatherApi
import com.example.weathercompose.domain.repository.WeatherRepository
import com.example.weathercompose.domain.util.Resource
import com.example.weathercompose.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherData(lat: String, long: String): Resource<WeatherInfo> {
        return try {
            Resource.Success(data = api.getWeatherData(lat, long).toWeatherInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error detected")
        }
    }
}