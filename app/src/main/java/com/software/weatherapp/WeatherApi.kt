package com.software.weatherapp

import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("/v1/forecast?latitude=28.535517&longitude=77.391029&current=temperature_2m,wind_speed_10m")
    suspend fun getWeather() : Response<Weather>
}