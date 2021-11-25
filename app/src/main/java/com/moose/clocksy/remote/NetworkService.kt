package com.moose.clocksy.remote

import com.moose.clocksy.domain.Weather
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json as json

object NetworkService {

    private const val api_key = "<YOUR_API_KEY_HERE>"
    private const val base_url = "https://api.openweathermap.org/data/2.5/"

    private val client = HttpClient{
        install(Logging)
        install(JsonFeature){
            serializer = KotlinxSerializer(json{ignoreUnknownKeys = true})
        }
    }

    suspend fun getData(lat: Double, lon: Double): Weather {
        val endpoint = "${base_url}weather?lat=$lat&lon=$lon&appid=$api_key"
        return client.get(endpoint)
    }
}