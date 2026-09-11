package com.example.listycity

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val cities = mutableStateListOf(
        "Edmonton", "Calgary", "Vancouver", "Toronto", "Montreal"
    )

    fun getCities(): List<String> = cities

    fun addCity(name: String) {
        val trimmed = name.trim()
        if (trimmed.isNotBlank() && !cities.contains(trimmed)) {
            cities.add(trimmed)
        }
    }

    fun deleteCity(name: String) {
        cities.remove(name)
    }
}