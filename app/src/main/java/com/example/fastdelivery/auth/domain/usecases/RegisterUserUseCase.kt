package com.example.fastdelivery.auth.domain.usecases

import com.example.fastdelivery.auth.domain.model.RegisterRequest
import com.example.fastdelivery.auth.domain.model.RegisterResponse
import com.example.fastdelivery.auth.domain.repository.AuthRepository

class RegisterUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: RegisterRequest): RegisterResponse {
        return repository.register(request)
    }
}