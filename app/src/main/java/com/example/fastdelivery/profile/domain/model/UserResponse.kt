package com.example.fastdelivery.profile.domain.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("_id")
    val id: String,

    val email: String,

    val fullName: String,

    val encryptedPassword: String,

    val createdAt: String,

    val updatedAt: String,

    @SerializedName("__v")
    val version: Int,

    val nationality: String? = null,

    val imageUrl: String? = null
)
