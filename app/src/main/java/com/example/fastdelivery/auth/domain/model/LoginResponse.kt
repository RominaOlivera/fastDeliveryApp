package com.example.fastdelivery.auth.domain.model

data class LoginResponse(
    val message: String,
    val userId: String? = null
)
