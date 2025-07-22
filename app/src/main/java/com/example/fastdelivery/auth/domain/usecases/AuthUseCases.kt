package com.example.fastdelivery.auth.domain.usecases

class AuthUseCases(
    val loginUser: LoginUserUseCase,
    val registerUser: RegisterUserUseCase
)