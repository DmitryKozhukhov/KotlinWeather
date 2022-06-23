package com.dmitrykozhukhov.kotlinweather.data

import com.dmitrykozhukhov.kotlinweather.domain.entities.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
