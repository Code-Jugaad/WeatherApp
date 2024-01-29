package com.software.weatherapp

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale


class MainActivity : AppCompatActivity() {

    lateinit var locationName: String
    lateinit var progressBar: ProgressBar
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtLocation = findViewById<TextView>(R.id.txt_location)
        val txtTemp = findViewById<TextView>(R.id.txt_temp)
        val txtWindSpeed = findViewById<TextView>(R.id.txt_wind_speed)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)



        val weatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)

            // launching a new coroutine
            GlobalScope.launch {
                val result = weatherApi.getWeather()
                withContext(Dispatchers.Main) {
                    if (result != null) {
                        // Checking the results
                        progressBar.visibility = View.INVISIBLE
                        txtTemp.text = "${result.body()?.current?.temperature_2m}${result.body()?.current_units?.temperature_2m}"
                        txtWindSpeed.text = "${result.body()?.current?.wind_speed_10m} ${result.body()?.current_units?.wind_speed_10m}"
                        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())

                        val latitude = result.body()?.latitude
                        val longitude = result.body()?.longitude
                        try {
                            val addresses = geocoder.getFromLocation(
                                latitude!!,
                                longitude!!, 1
                            )
                            if (addresses != null && addresses.size > 0) {
                                val address = addresses[0]
                                locationName = address.getAddressLine(0)
                            } else {
                                txtLocation.text = "No location"
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        txtLocation.text = locationName ?: "NO location"
                    } else {
                        txtTemp.text = "No results found"
                    }
                }
            }
    }
}