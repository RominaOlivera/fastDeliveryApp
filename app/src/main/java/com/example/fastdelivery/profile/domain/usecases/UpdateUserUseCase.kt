package com.example.fastdelivery.profile.domain.usecases

import com.example.fastdelivery.profile.data.local.UserDao
import com.example.fastdelivery.profile.data.local.UserEntity
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userDao: UserDao
) {
    suspend operator fun invoke(user: UserEntity) {
        userDao.upsertUser(user)
    }
}
