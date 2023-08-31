package com.example.weathercompose.di

import com.example.weathercompose.data.location.WeatherLocationImpl
import com.example.weathercompose.domain.location.WeatherLocation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(weatherLocationImpl: WeatherLocationImpl): WeatherLocation
}