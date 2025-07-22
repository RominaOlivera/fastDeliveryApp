package com.example.fastdelivery.auth.data

import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.LoginResponse
import com.example.fastdelivery.auth.domain.model.RegisterRequest
import com.example.fastdelivery.auth.domain.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse

}
