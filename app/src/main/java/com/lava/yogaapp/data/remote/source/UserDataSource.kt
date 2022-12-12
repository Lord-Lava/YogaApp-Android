package com.lava.yogaapp.data.remote.source

import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.model.registration.RegistrationResponse

interface UserDataSource {

    suspend fun signUp(username: String, password: String): Resource<LoginResponse>
    suspend fun signIn(username: String, password: String): Resource<LoginResponse>
    suspend fun authenticate(): Resource<AuthenticateResponse>
    suspend fun registerUser(
        name: String,
        age: Int,
        slot: String,
        amount: Int,
    ): Resource<RegistrationResponse>
}