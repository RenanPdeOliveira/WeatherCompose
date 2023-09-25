package com.example.weathercompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.weathercompose.R
import com.example.weathercompose.presentation.ui.theme.DarkBlue
import com.example.weathercompose.presentation.ui.theme.DeepBlue
import com.example.weathercompose.presentation.ui.theme.WeatherComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
                    viewModel.state.error?.let { error ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(280.dp)
                                    .padding(bottom = 16.dp),
                                painter = painterResource(id = R.drawable.undraw_signal_searching),
                                contentDescription = null,
                            )
                            Text(
                                text = error,
                                color = Color.White,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            )
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp),
                                onClick = {
                                    viewModel.loadWeatherInfo()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DeepBlue
                                )
                            ) {
                                Text(
                                    text = "Try again",
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 16.dp)
                    ) {
                        ShimmerMainLayout(
                            isLoading = viewModel.state.isLoading,
                            weatherCardContent = {
                                WeatherCard(
                                    state = viewModel.state,
                                    cityState = viewModel.cityState,
                                    backgroundColor = CardDefaults.cardColors(containerColor = DeepBlue)
                                )
                            },
                            weatherForecastContent = {
                                WeatherForecast(
                                    state = viewModel.state,
                                    backgroundColor = CardDefaults.cardColors(containerColor = DeepBlue)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}