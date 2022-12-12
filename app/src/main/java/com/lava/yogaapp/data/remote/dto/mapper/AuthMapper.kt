package com.lava.yogaapp.data.remote.dto.mapper

import com.lava.yogaapp.data.remote.dto.AuthenticateResponseDto
import com.lava.yogaapp.data.remote.dto.LoginResponseDto
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        jwtAuthToken = jwtAuthToken,
        isRegistered = isRegistered,
        uid = uid
    )
}

fun AuthenticateResponseDto.toAuthenticateResponse(): AuthenticateResponse {
    return AuthenticateResponse(
        isRegistered = isRegistered
    )
}