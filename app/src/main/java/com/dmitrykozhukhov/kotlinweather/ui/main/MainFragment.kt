package com.dmitrykozhukhov.kotlinweather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dmitrykozhukhov.kotlinweather.R
import com.dmitrykozhukhov.kotlinweather.databinding.MainFragmentBinding
import com.dmitrykozhukhov.kotlinweather.data.AppState
import com.dmitrykozhukhov.kotlinweather.domain.entities.Weather
import com.dmitrykozhukhov.kotlinweather.ui.adapters.MainFragmentAdapter
import com.dmitrykozhukhov.kotlinweather.ui.details.DetailsFragment
import com.dmitrykozhukhov.kotlinweather.ui.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetRus = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }

            val observer = Observer<AppState> { renderData(it) }
            viewModel.liveData.observe(viewLifecycleOwner, observer)
            viewModel.getWeatherFromLocalSourceRus()
        }
    }

    private fun changeWeatherDataSet() = with(binding) {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(weather: Weather) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, DetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setWeather(appState.weatherData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                mainFragmentRecyclerView.showSnackBar(
                    R.string.error,
                    R.string.reload,
                    { viewModel.getWeatherFromLocalSourceRus() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}