package com.moose.clocksy.domain

import kotlinx.serialization.Serializable

@Serializable
data class Weather(val wind: Wind, val main: Main, val name: String, val clouds: Clouds)

@Serializable
data class Clouds(val all: Int) // percentage

@Serializable
data class Main(val temp: Double, val humidity: Int, val pressure: Int)

@Serializable
data class Wind(val speed: Double) // meter/sec