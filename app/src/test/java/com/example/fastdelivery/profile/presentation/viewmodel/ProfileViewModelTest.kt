package com.example.fastdelivery.profile.presentation.viewmodel

import com.example.fastdelivery.profile.data.local.UserEntity
import com.example.fastdelivery.profile.domain.usecases.GetUserByEmailUseCase
import com.example.fastdelivery.profile.domain.usecases.UpdateUserUseCase
import com.example.fastdelivery.profile.domain.usecases.UploadUserImageUseCase
import com.example.fastdelivery.profile.presentation.ProfileViewModel
import com.example.fastdelivery.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getUserByEmail: GetUserByEmailUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk(relaxed = true)
    private val uploadUserImageUseCase: UploadUserImageUseCase = mockk()

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        viewModel = ProfileViewModel(getUserByEmail, updateUserUseCase, uploadUserImageUseCase)
    }

    @Test
    fun `fetchUserByEmail emits user when success`() = runTest {
        val email = "test@example.com"
        val user = UserEntity("1", "Test User", email, "Argentina", null)

        coEvery { getUserByEmail(email) } returns flowOf(Result.success(user))

        viewModel.fetchUserByEmail(email)

        assertEquals(user, viewModel.user.value)
        assertNull(viewModel.errorMessage.value)

        coVerify { getUserByEmail(email) }
    }

    @Test
    fun `fetchUserByEmail emits error when failure`() = runTest {
        val email = "fail@example.com"
        val exception = Exception("User not found")

        coEvery { getUserByEmail(email) } returns flowOf(Result.failure(exception))

        viewModel.fetchUserByEmail(email)

        assertNull(viewModel.user.value)
        assertEquals("User not found", viewModel.errorMessage.value)

        coVerify { getUserByEmail(email) }
    }
}
