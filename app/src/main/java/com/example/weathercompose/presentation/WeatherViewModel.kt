package com.example.weathercompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercompose.domain.cities.cities
import com.example.weathercompose.domain.repository.WeatherRepository
import com.example.weathercompose.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    var cityState by mutableStateOf(CityState())
        private set

    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _listOfCities = MutableStateFlow(cities)
    val listOfCities = searchText.combine(_listOfCities) { text, cities ->
        if (text.isBlank()) {
            cities
        } else {
            cities.filter {
                it.doesMatchSearch(text)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _listOfCities.value
    )

    fun onSearchText(text: String) {
        _searchText.value = text
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {
            delay(500L)
            state = state.copy(
                isLoading = true,
                error = null
            )
            when (val result = repository.getWeatherData(cityState.lat, cityState.long)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}