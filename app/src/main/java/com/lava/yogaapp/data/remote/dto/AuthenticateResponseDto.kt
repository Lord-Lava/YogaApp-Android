package com.lava.yogaapp.data.remote.dto

import com.squareup.moshi.Json

data class AuthenticateResponseDto(
    @Json(name = "isRegistered")
    val isRegistered: Boolean,
)