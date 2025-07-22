package com.example.fastdelivery.auth.domain.model

data class RegisterResponse(
    val message: String,
    val userId: String? = null
)