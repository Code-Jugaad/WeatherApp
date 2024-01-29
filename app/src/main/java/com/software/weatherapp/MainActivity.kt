package com.software.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        val txtTemp = findViewById<TextView>(R.id.txt_temp)

        val weatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)

            // launching a new coroutine
            GlobalScope.launch {
                val result = weatherApi.getWeather()
                withContext(Dispatchers.Main) {
                    if (result != null) {
                        // Checking the results
                        txtTemp.text = "${result.body()?.current?.temperature_2m}"
                    } else {
                        txtTemp.text = "null results"
                    }
                }
            }
    }
}