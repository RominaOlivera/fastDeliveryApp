package com.example.fastdelivery.profile.domain.usecases

import com.example.fastdelivery.profile.data.local.UserDao
import com.example.fastdelivery.profile.data.local.UserEntity
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateUserUseCaseTest {

    private val userDao: UserDao = mockk(relaxed = true)
    private lateinit var useCase: UpdateUserUseCase

    @Before
    fun setup() {
        useCase = UpdateUserUseCase(userDao)
    }

    @Test
    fun `invoke calls upsertUser on userDao`() = runTest {
        val user = UserEntity("1", "Name", "email@example.com", "Nationality", null)

        useCase(user)

        coVerify { userDao.upsertUser(user) }
    }
}
