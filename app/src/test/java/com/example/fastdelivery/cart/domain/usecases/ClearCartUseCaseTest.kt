package com.example.fastdelivery.cart.domain.usecases

import com.example.fastdelivery.cart.domain.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ClearCartUseCaseTest {

    private val repository: CartRepository = mockk(relaxed = true)
    private lateinit var useCase: ClearCartUseCase

    @Before
    fun setup() {
        useCase = ClearCartUseCase(repository)
    }

    @Test
    fun `invoke should call repository clearCart`() = runTest {
        useCase()
        coVerify { repository.clearCart() }
    }
}
