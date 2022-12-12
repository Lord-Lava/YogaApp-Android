package com.lava.yogaapp.data.remote.service

import com.lava.yogaapp.data.remote.dto.AuthenticateResponseDto
import com.lava.yogaapp.data.remote.dto.LoginResponseDto
import com.lava.yogaapp.data.remote.dto.RegistrationResponseDto
import com.lava.yogaapp.domain.model.auth.LoginRequest
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.model.registration.RegistrationRequest
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("signup")
    suspend fun signUp(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponseDto>

    @POST("signin")
    suspend fun signIn(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponseDto>

    @POST("register")
    suspend fun registerUser(
        @Body registrationRequest: RegistrationRequest,
    ): Response<RegistrationResponseDto>

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String,
        @Query("uid") uid: String,
    ): Response<AuthenticateResponseDto>
}