package com.oysi.model.nearestcity

data class Data(
    val city: String,
    val country: String,
    val current: Current,
    val location: Location,
    val state: String
)