package com.example.fastdelivery.profile.data.remote

import com.example.fastdelivery.profile.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserResponse>
}
