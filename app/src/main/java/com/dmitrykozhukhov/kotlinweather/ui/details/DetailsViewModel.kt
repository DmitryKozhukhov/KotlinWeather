package com.dmitrykozhukhov.kotlinweather.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrykozhukhov.kotlinweather.data.AppState
import com.dmitrykozhukhov.kotlinweather.data.repository.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val weatherLiveData: LiveData<AppState> get() = localLiveData

    fun loadData(lat: Double, lon: Double) {
        localLiveData.value = AppState.Loading
        Thread {
            val data = repository.getWeatherFromServer(lat, lon)
            localLiveData.postValue(AppState.Success(listOf(data)))
        }.start()
    }
}