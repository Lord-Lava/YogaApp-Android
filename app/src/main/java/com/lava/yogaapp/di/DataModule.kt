package com.lava.yogaapp.di

import com.lava.yogaapp.data.remote.source.UserData
import com.lava.yogaapp.data.remote.source.UserDataSource
import com.lava.yogaapp.data.repository.UserRepositoryImpl
import com.lava.yogaapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideUserDataSource(userData: UserData): UserDataSource
}