package com.example.weathercompose.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weathercompose.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

@Composable
fun HourlyDataDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val weatherDataTime = remember(weatherData) {
            weatherData.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
        Text(
            text = weatherDataTime,
            color = Color.White
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.icon),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperature}ºC",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}