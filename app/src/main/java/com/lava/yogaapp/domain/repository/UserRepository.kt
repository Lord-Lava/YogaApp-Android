package com.lava.yogaapp.domain.repository

import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signUp(username: String, password: String): Flow<Resource<LoginResponse>>
    suspend fun signIn(username: String, password: String): Flow<Resource<LoginResponse>>
    suspend fun authenticate(): Flow<Resource<AuthenticateResponse>>
    suspend fun registerUser(name: String, age: Int, slot: String, amount: Int): Flow<Resource<RegistrationResponse>>
}