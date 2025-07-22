package com.example.fastdelivery.auth.data

import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.LoginResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private lateinit var authApi: AuthApi
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setup() {
        authApi = mockk()
        authRepository = AuthRepositoryImpl(authApi)
    }

    @Test
    fun `login should call authApi loginUser and return response`() = runBlocking {
        val request = LoginRequest("test@example.com", "password")
        val expectedResponse = LoginResponse("Login successful", "userId123")

        coEvery { authApi.loginUser(request) } returns expectedResponse

        val actualResponse = authRepository.login(request)

        assertEquals(expectedResponse, actualResponse)
    }
}
