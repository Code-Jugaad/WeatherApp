package com.software.weatherapp

import android.annotation.SuppressLint
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtLocation = findViewById<TextView>(R.id.txt_location)
        val txtTemp = findViewById<TextView>(R.id.txt_temp)
        val txtWindSpeed = findViewById<TextView>(R.id.txt_wind_speed)

        val weatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)

            // launching a new coroutine
            GlobalScope.launch {
                val result = weatherApi.getWeather()
                withContext(Dispatchers.Main) {
                    if (result != null) {
                        // Checking the results
                        txtTemp.text = "${result.body()?.current?.temperature_2m}${result.body()?.current_units?.temperature_2m}"
                        txtWindSpeed.text = "${result.body()?.current?.wind_speed_10m} ${result.body()?.current_units?.wind_speed_10m}"

                        txtLocation.text = "${result.body()?.latitude} ${result.body()?.longitude}"
                    } else {
                        txtTemp.text = "null results"
                    }
                }
            }
    }
}