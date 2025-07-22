package com.example.fastdelivery.profile.domain.usecases

import com.example.fastdelivery.profile.data.local.UserDao
import com.example.fastdelivery.profile.data.local.toEntity
import com.example.fastdelivery.profile.data.remote.UserApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi
) {
    operator fun invoke(email: String) = flow {
        val localUser = userDao.getUserByEmail(email)
        if (localUser != null) {
            emit(Result.success(localUser))
        } else {
            val response = userApi.getUserByEmail(email)
            if (response.isSuccessful) {
                val remoteUser = response.body()!!.toEntity()
                userDao.upsertUser(remoteUser)
                emit(Result.success(remoteUser))
            } else {
                emit(Result.failure(Exception("Error: ${response.code()}")))
            }
        }
    }.catch { e ->
        emit(Result.failure(e))
    }
}
