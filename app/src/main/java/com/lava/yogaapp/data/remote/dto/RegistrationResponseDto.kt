package com.lava.yogaapp.data.remote.dto

import com.squareup.moshi.Json

data class RegistrationResponseDto(
    @Json(name = "age")
    val age: Int,
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "slot")
    val slot: String,
    @Json(name = "uid")
    val uid: String,
)