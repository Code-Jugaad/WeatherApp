package com.software.weatherapp

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offsets_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Int,
    val current_units: CurrentUnits,
    val current: Current

)
