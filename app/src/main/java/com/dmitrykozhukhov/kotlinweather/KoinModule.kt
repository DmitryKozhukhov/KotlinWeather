package com.dmitrykozhukhov.kotlinweather

import com.dmitrykozhukhov.kotlinweather.data.repository.Repository
import com.dmitrykozhukhov.kotlinweather.data.repository.RepositoryImpl
import com.dmitrykozhukhov.kotlinweather.ui.details.DetailsViewModel
import com.dmitrykozhukhov.kotlinweather.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

}