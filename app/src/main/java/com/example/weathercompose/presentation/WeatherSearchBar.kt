package com.example.weathercompose.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercompose.domain.cities.City
import com.example.weathercompose.presentation.ui.theme.DeepBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSearchBar(
    searchText: String,
    cities: List<City>,
    state: WeatherState,
    cityState: CityState,
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentlyWeatherData?.let {
        var active by remember {
            mutableStateOf(false)
        }
        SearchBar(
            modifier = modifier
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
                items(
                    cities.sortedBy {
                        it.city
                    }) { city ->
                    Text(
                        modifier = Modifier
                            .clickable {
                                cityState.city = city.city
                                cityState.lat = city.lat
                                cityState.long = city.long
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
    }
}