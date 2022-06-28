package com.dmitrykozhukhov.kotlinweather.data.repository

import com.dmitrykozhukhov.kotlinweather.data.WeatherLoader
import com.dmitrykozhukhov.kotlinweather.domain.entities.Weather
import com.dmitrykozhukhov.kotlinweather.domain.entities.getRussianCities
import com.dmitrykozhukhov.kotlinweather.domain.entities.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(lat: Double, lon: Double): Weather {
        val dto = WeatherLoader.loadWeather(lat, lon)
        return Weather(
            temperature = dto?.current?.temp ?: 0.0,
            feelsLike = dto?.current?.feelsLike ?: 0.0,
            description = dto?.current?.weatherDTO?.get(0)?.description
        )
    }

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}