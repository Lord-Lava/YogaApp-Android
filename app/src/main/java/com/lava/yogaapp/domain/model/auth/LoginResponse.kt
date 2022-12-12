package com.lava.yogaapp.domain.model.auth

data class LoginResponse(
    val jwtAuthToken: String,
    val isRegistered: Boolean,
    val uid: String,
)
