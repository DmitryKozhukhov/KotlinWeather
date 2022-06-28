package com.dmitrykozhukhov.kotlinweather.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmitrykozhukhov.kotlinweather.databinding.FragmentMainRecyclerItemBinding
import com.dmitrykozhukhov.kotlinweather.domain.entities.Weather
import com.dmitrykozhukhov.kotlinweather.ui.main.MainFragment

class MainFragmentAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData: List<Weather> = listOf()
    private lateinit var binding: FragmentMainRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = FragmentMainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) = with(binding) {
            mainFragmentRecyclerItemTextView.text = weather.city.city
            root.setOnClickListener { itemClickListener.onItemViewClick(weather) }
        }
    }
}