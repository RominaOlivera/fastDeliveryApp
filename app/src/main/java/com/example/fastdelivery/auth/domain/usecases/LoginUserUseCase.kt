package com.example.fastdelivery.auth.domain.usecases

import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.LoginResponse
import com.example.fastdelivery.auth.domain.repository.AuthRepository

class LoginUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest): LoginResponse {
        return repository.login(request)
    }
}