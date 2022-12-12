package com.lava.yogaapp.data.remote.source

import com.lava.yogaapp.data.error.NETWORK_ERROR
import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.data.error.NO_INTERNET_CONNECTION
import com.lava.yogaapp.data.error.UNAUTHORIZED
import com.lava.yogaapp.data.local.SharedPrefs
import com.lava.yogaapp.data.remote.dto.AuthenticateResponseDto
import com.lava.yogaapp.data.remote.dto.LoginResponseDto
import com.lava.yogaapp.data.remote.dto.RegistrationResponseDto
import com.lava.yogaapp.data.remote.dto.mapper.toLoginResponse
import com.lava.yogaapp.data.remote.dto.mapper.toAuthenticateResponse
import com.lava.yogaapp.data.remote.dto.mapper.toRegistrationResponse
import com.lava.yogaapp.data.remote.service.UserApiService
import com.lava.yogaapp.domain.model.auth.LoginRequest
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.model.registration.RegistrationRequest
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import com.lava.yogaapp.util.JWT_AUTH_TOKEN
import com.lava.yogaapp.util.NetworkConnectivity
import com.lava.yogaapp.util.UID
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UserData @Inject constructor(
    private val userApiService: UserApiService,
    private val networkConnectivity: NetworkConnectivity,
    private val sharedPrefs: SharedPrefs,
) : UserDataSource {

    override suspend fun signUp(username: String, password: String): Resource<LoginResponse> {
        return when (val response = processCall {
            userApiService.signUp(
                LoginRequest(username, password)
            )
        }) {
            is LoginResponseDto -> {
                Resource.Success(data = response.toLoginResponse())
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun signIn(username: String, password: String): Resource<LoginResponse> {
        return when (val response = processCall {
            userApiService.signIn(
                LoginRequest(username, password)
            )
        }) {
            is LoginResponseDto -> {
                sharedPrefs.clear()
                sharedPrefs.putString(JWT_AUTH_TOKEN, response.jwtAuthToken)
                sharedPrefs.putString(UID, response.uid)
                Resource.Success(data = response.toLoginResponse())
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun authenticate(): Resource<AuthenticateResponse> {
        val token = sharedPrefs.getString(JWT_AUTH_TOKEN)
            ?: return Resource.DataError(errorCode = UNAUTHORIZED)
        val uid = sharedPrefs.getString(UID) ?: return Resource.DataError(errorCode = UNAUTHORIZED)
        return when (val response = processCall { userApiService.authenticate("Bearer $token", uid) }) {
            is AuthenticateResponseDto -> {
                Resource.Success(response.toAuthenticateResponse())
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun registerUser(
        name: String,
        age: Int,
        slot: String,
        amount: Int,
    ): Resource<RegistrationResponse> {
        val uid = sharedPrefs.getString(UID) ?: return Resource.DataError(errorCode = UNAUTHORIZED)
        return when (val response =
            processCall {
                userApiService.registerUser(
                    RegistrationRequest(
                        uid, name, age, slot, amount
                    )
                )
            }) {
            is RegistrationResponseDto -> {
                Resource.Success(data = response.toRegistrationResponse())
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

}