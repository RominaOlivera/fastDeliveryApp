package com.example.fastdelivery.auth.domain.model

data class RegisterRequest(
    val email: String,
    val fullName: String,
    val encryptedPassword: String
)
