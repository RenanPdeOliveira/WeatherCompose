package com.example.weathercompose.domain.cities

data class City(
    val city: String,
    val lat: String,
    val long: String
) {
    fun doesMatchSearch(query: String): Boolean {
        val matchingCombinations = listOf(
            "$city",
            "${city.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

val cities = listOf(
    City("São Paulo", "-23.55", "-46.6333"),
    City("Tokyo", "35.6897", "139.6922"),
    City("Cairo", "30.0444", "31.2358"),
    City("New York", "40.6943", "-73.9249"),
    City("Bangkok", "13.7525", "100.4942"),
    City("Moscow", "55.7558", "37.6178"),
    City("Buenos Aires", "-34.5997", "-58.3819"),
    City("Istanbul", "41.0136", "28.955"),
    City("Rio de Janeiro", "-22.9111", "-43.2056"),
    City("Los Angeles", "34.1141", "-118.4068"),
)