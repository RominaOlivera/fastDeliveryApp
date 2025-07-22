package com.example.fastdelivery.auth.domain.model

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)
