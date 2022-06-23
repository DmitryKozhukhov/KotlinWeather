package com.dmitrykozhukhov.kotlinweather.domain.entities.rest_entities

import com.google.gson.annotations.SerializedName


data class CurrentDTO(
    val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    val weatherDTO: List<WeatherDetailsDTO>
)
