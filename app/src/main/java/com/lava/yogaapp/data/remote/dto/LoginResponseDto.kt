package com.lava.yogaapp.data.remote.dto

import com.squareup.moshi.Json

data class LoginResponseDto(
    @Json(name = "jwtAuthToken")
    val jwtAuthToken: String,
    @Json(name = "uid")
    val uid: String,
    @Json(name = "isRegistered")
    val isRegistered: Boolean,
)