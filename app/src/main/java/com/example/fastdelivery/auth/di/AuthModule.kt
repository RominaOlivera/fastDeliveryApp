package com.example.fastdelivery.auth.di

import com.example.fastdelivery.auth.data.AuthApi
import com.example.fastdelivery.auth.data.AuthRepositoryImpl
import com.example.fastdelivery.auth.domain.repository.AuthRepository
import com.example.fastdelivery.auth.domain.usecases.AuthUseCases
import com.example.fastdelivery.auth.domain.usecases.LoginUserUseCase
import com.example.fastdelivery.auth.domain.usecases.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            loginUser = LoginUserUseCase(authRepository),
            registerUser = RegisterUserUseCase(authRepository)
        )
    }
}