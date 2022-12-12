package com.lava.yogaapp.data.repository

import com.lava.yogaapp.data.Resource
import com.lava.yogaapp.data.remote.source.UserDataSource
import com.lava.yogaapp.domain.model.auth.AuthenticateResponse
import com.lava.yogaapp.domain.model.auth.LoginResponse
import com.lava.yogaapp.domain.model.registration.RegistrationResponse
import com.lava.yogaapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val ioDispatcher: CoroutineContext,
) : UserRepository {

    override suspend fun signUp(username: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(userDataSource.signUp(username, password))
        }.flowOn(ioDispatcher)
    }

    override suspend fun signIn(username: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(userDataSource.signIn(username, password))
        }.flowOn(ioDispatcher)
    }

    override suspend fun authenticate(): Flow<Resource<AuthenticateResponse>> {
        return flow {
            emit(userDataSource.authenticate())
        }.flowOn(ioDispatcher)
    }

    override suspend fun registerUser(
        name: String,
        age: Int,
        slot: String,
        amount: Int,
    ): Flow<Resource<RegistrationResponse>> {
        return flow {
            emit(userDataSource.registerUser(
                name, age, slot, amount
            ))
        }.flowOn(ioDispatcher)
    }
}