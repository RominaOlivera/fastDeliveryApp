package com.example.fastdelivery.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastdelivery.auth.domain.model.LoginRequest
import com.example.fastdelivery.auth.domain.model.RegisterRequest
import com.example.fastdelivery.auth.domain.usecases.AuthUseCases
import com.example.fastdelivery.auth.domain.usecases.LoginUserUseCase
import com.example.fastdelivery.auth.domain.usecases.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _registerState = MutableStateFlow<String?>(null)
    val registerState: StateFlow<String?> = _registerState

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    fun register(email: String, fullName: String, encryptedPassword: String) {
        viewModelScope.launch {
            try {
                val response = authUseCases.registerUser(
                    RegisterRequest(email, fullName, encryptedPassword)
                )
                _registerState.value = "Success: ${response.message}"
            } catch (e: Exception) {
                _registerState.value = "Error: The user is already registered"
            }
        }
    }

    fun login(email: String, encryptedPassword: String) {
        viewModelScope.launch {
            try {
                val response = authUseCases.loginUser(
                    LoginRequest(email, encryptedPassword)
                )
                _loginState.value = "Success: User authenticated"
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> _loginState.value = "Error: Incorrect password"
                    404 -> _loginState.value = "Error: User not found"
                    500 -> _loginState.value = "Error: Server error"
                    else -> _loginState.value = "Error: Invalid credentials"
                }
            } catch (e: Exception) {
                _loginState.value = "Error: ${e.localizedMessage ?: "Invalid credentials"}"
            }
        }
    }

    fun clearRegisterState() {
        _registerState.value = null
    }

    fun clearLoginState() {
        _loginState.value = null
    }

    fun setRegisterError(message: String) {
        _registerState.value = "Error: $message"
    }
}