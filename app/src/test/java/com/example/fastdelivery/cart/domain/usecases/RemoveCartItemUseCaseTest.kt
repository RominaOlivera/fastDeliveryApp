package com.example.fastdelivery.cart.domain.usecases

import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.repository.CartRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoveCartItemUseCaseTest {

    private val repository: CartRepository = mockk(relaxed = true)
    private lateinit var useCase: RemoveCartItemUseCase

    @Before
    fun setup() {
        useCase = RemoveCartItemUseCase(repository)
    }

    @Test
    fun `invoke should call repository removeItem`() = runTest {
        val item = CartItemEntity("1", "Test", 10.0, 1, "image")
        useCase(item)
        coVerify { repository.removeItem(item) }
    }
}