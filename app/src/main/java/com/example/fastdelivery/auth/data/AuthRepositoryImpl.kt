package com.example.fastdelivery.auth.data

import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.LoginResponse
import com.example.fastdelivery.auth.domain.model.RegisterRequest
import com.example.fastdelivery.auth.domain.model.RegisterResponse
import com.example.fastdelivery.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return authApi.loginUser(request)
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return authApi.registerUser(request)
    }
}
