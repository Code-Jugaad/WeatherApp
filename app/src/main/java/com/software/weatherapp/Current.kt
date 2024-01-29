package com.software.weatherapp

data class Current(
    val time:String,
    val interval:Int,
    val temperature_2m:Double,
    val wind_speed_10m:Double
)
