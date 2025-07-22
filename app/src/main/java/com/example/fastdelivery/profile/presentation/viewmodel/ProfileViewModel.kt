package com.example.fastdelivery.profile.presentation

import com.example.fastdelivery.profile.domain.usecases.GetUserByEmailUseCase
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastdelivery.profile.data.local.UserEntity
import com.example.fastdelivery.profile.domain.usecases.UpdateUserUseCase
import com.example.fastdelivery.profile.domain.usecases.UploadUserImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByEmail: GetUserByEmailUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadUserImageUseCase: UploadUserImageUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUserByEmail(email: String) {
        viewModelScope.launch {
            getUserByEmail(email).collect { result ->
                result.onSuccess {
                    _user.value = it
                }.onFailure {
                    _errorMessage.value = it.localizedMessage
                }
            }
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            updateUserUseCase(user)
            _user.value = user
        }
    }

    fun uploadImageToCloudinary(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            val result = uploadUserImageUseCase(context.contentResolver, imageUri)
            result.onSuccess { secureUrl ->
                _user.value?.let { currentUser ->
                    val updatedUser = currentUser.copy(imageUrl = secureUrl)
                    updateUser(updatedUser)
                }
                _errorMessage.value = null
            }.onFailure {
                _errorMessage.value = "Image upload failed: ${it.localizedMessage}"
            }
        }
    }

}
