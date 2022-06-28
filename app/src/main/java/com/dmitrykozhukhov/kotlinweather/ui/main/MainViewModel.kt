package com.dmitrykozhukhov.kotlinweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrykozhukhov.kotlinweather.data.AppState
import com.dmitrykozhukhov.kotlinweather.data.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val localLiveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> get() = localLiveData

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        localLiveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            localLiveData.postValue(
                if (isRussian) {
                    AppState.Success(repository.getWeatherFromLocalStorageRus())
                } else {
                    AppState.Success(repository.getWeatherFromLocalStorageWorld())
                }
            )
        }.start()
    }
}