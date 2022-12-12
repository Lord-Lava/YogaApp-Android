package com.lava.yogaapp.domain.model.auth

data class LoginRequest(
    val username: String,
    val password: String,
)