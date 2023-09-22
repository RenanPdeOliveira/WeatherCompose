package com.example.weathercompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weathercompose.presentation.ui.theme.DarkBlue
import com.example.weathercompose.presentation.ui.theme.DeepBlue
import com.example.weathercompose.presentation.ui.theme.WeatherComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.loadWeatherInfo()
            val searchText by viewModel.searchText.collectAsState()
            val cities by viewModel.listOfCities.collectAsState()
            WeatherComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                ) {
                    WeatherSearchBar(
                        searchText = searchText,
                        cities = cities,
                        state = viewModel.state,
                        cityState = viewModel.cityState,
                        viewModel = viewModel
                    )
                    WeatherCard(
                        state = viewModel.state,
                        cityState = viewModel.cityState,
                        backgroundColor = CardDefaults.cardColors(containerColor = DeepBlue)
                    )
                    WeatherForecast(
                        state = viewModel.state,
                        backgroundColor = CardDefaults.cardColors(containerColor = DeepBlue)
                    )
                    if (viewModel.state.isLoading) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    viewModel.state.error?.let { error ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = error,
                                color = Color.White,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}