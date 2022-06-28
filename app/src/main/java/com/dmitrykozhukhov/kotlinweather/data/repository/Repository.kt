package com.dmitrykozhukhov.kotlinweather.data.repository

import com.dmitrykozhukhov.kotlinweather.domain.entities.Weather

interface Repository {
    fun getWeatherFromServer(lat: Double, lon: Double): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}