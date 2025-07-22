package com.example.fastdelivery.auth.presentation.viewmodel

import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.LoginResponse
import com.example.fastdelivery.auth.domain.model.RegisterRequest
import com.example.fastdelivery.auth.domain.model.RegisterResponse
import com.example.fastdelivery.auth.domain.usecases.AuthUseCases
import com.example.fastdelivery.auth.domain.usecases.LoginUserUseCase
import com.example.fastdelivery.auth.domain.usecases.RegisterUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var authUseCases: AuthUseCases
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginUserUseCase = mockk()
        registerUserUseCase = mockk()
        authUseCases = AuthUseCases(loginUserUseCase, registerUserUseCase)
        authViewModel = AuthViewModel(authUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates loginState`() = runTest {
        val request = LoginRequest("test@example.com", "12345678")
        val response = LoginResponse("Login successful", "userId123")

        coEvery { authUseCases.loginUser.invoke(request) } returns response

        authViewModel.login(request.email, request.encryptedPassword)
        advanceUntilIdle()

        assertEquals("Success: User authenticated", authViewModel.loginState.value)
    }

    @Test
    fun `register success updates registerState`() = runTest {
        val request = RegisterRequest("test@example.com", "Test User", "123456")
        val response = RegisterResponse("User registered", "userId123")

        coEvery { authUseCases.registerUser.invoke(request) } returns response

        authViewModel.register(request.email, request.fullName, request.encryptedPassword)
        advanceUntilIdle()

        assertEquals("Success: ${response.message}", authViewModel.registerState.value)
    }

    @Test
    fun `clearLoginState sets loginState to null`() {
        authViewModel.clearLoginState()
        assertEquals(null, authViewModel.loginState.value)
    }

    @Test
    fun `clearRegisterState sets registerState to null`() {
        authViewModel.clearRegisterState()
        assertEquals(null, authViewModel.registerState.value)
    }
}