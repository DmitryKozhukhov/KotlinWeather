package com.dmitrykozhukhov.kotlinweather.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.dmitrykozhukhov.kotlinweather.domain.entities.rest_entities.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun loadWeather(lat: Double, lon: Double): WeatherDTO? {
        val uri =
            URL("https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&appid=abfdf53e141c68ef0a1604aae3026746&units=metric&lang=ru")
        lateinit var urlConnection: HttpsURLConnection

        return try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(
                "Default", "abfdf53e141c68ef0a1604aae3026746"
            )
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getLinesForOld(bufferedReader)
            } else {
                getLines(bufferedReader)
            }

            Gson().fromJson(lines, WeatherDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            urlConnection.disconnect()
        }
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}