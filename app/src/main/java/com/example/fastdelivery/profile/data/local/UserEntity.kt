package com.example.fastdelivery.profile.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fastdelivery.profile.domain.model.UserResponse

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val email: String,
    val nationality: String?,
    val imageUrl: String?
)

fun UserResponse.toEntity(): UserEntity = UserEntity(
    id = id,
    fullName = fullName,
    email = email,
    nationality = nationality,
    imageUrl = imageUrl
)

fun UserEntity.toResponse(): UserResponse = UserResponse(
    id = id,
    fullName = fullName,
    email = email,
    encryptedPassword = "",
    createdAt = "",
    updatedAt = "",
    version = 0,
    nationality = nationality,
    imageUrl = imageUrl
)

