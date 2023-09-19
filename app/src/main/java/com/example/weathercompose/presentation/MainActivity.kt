package com.example.weathercompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercompose.presentation.ui.theme.DarkBlue
import com.example.weathercompose.presentation.ui.theme.DeepBlue
import com.example.weathercompose.presentation.ui.theme.WeatherComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.loadWeatherInfo()
            val searchText by viewModel.searchText.collectAsState()
            val cities by viewModel.listOfCities.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()
            var active by remember {
                mutableStateOf(false)
            }
            WeatherComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                ) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = if (!active) {
                                    16.dp
                                } else {
                                    0.dp
                                },
                                top = if (!active) {
                                    8.dp
                                } else {
                                    0.dp
                                },
                                end = if (!active) {
                                    16.dp
                                } else {
                                    0.dp
                                }
                            ),
                        query = searchText,
                        onQueryChange = viewModel::onSearchText,
                        onSearch = {
                            active = false
                        },
                        active = active,
                        onActiveChange = {
                            active = it
                        },
                        placeholder = {
                            Text(text = "Search city")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            if (active) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        active = false
                                    },
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear icon",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = SearchBarDefaults.colors(
                            containerColor = DeepBlue,
                            inputFieldColors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedPlaceholderColor = Color.White,
                                unfocusedPlaceholderColor = Color.White
                            )
                        )
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            items(cities) { city ->
                                Text(
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.cityState.lat = city.lat
                                            viewModel.cityState.long = city.long
                                            viewModel.loadWeatherInfo()
                                            active = false
                                        }
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                                    text = city.city,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    WeatherCard(
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
                }
            }
        }
    }
}