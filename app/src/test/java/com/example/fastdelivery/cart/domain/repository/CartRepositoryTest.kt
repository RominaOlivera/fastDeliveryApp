package com.example.fastdelivery.cart.domain.repository

import app.cash.turbine.test
import com.example.fastdelivery.cart.data.local.CartItemDao
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CartRepositoryTest {

    private lateinit var dao: CartItemDao
    private lateinit var repository: CartRepository

    @Before
    fun setup() {
        dao = mockk()
        every { dao.getCartItems() } returns flowOf(emptyList())
        repository = CartRepository(dao)
    }

    @Test
    fun `cartItems should emit from dao`() = runTest {
        val expectedItems = listOf(
            CartItemEntity("1", "Product 1", 10.0, 1, "image")
        )
        every { dao.getCartItems() } returns flowOf(expectedItems)
        val repo = CartRepository(dao)

        repo.cartItems.test {
            assertEquals(expectedItems, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addItem should insert when item does not exist`() = runTest {
        val item = CartItemEntity("1", "Product 1", 10.0, 1, "image")
        coEvery { dao.getItemByProductId(item.productId) } returns null
        coEvery { dao.insertItem(item) } just Runs

        repository.addItem(item)

        coVerify { dao.insertItem(item) }
    }

    @Test
    fun `addItem should increase quantity when item exists`() = runTest {
        val item = CartItemEntity("1", "Product 1", 10.0, 1, "image")
        coEvery { dao.getItemByProductId(item.productId) } returns item
        coEvery { dao.increaseQuantity(item.productId) } just Runs

        repository.addItem(item)

        coVerify { dao.increaseQuantity(item.productId) }
    }

    @Test
    fun `removeItem should call dao delete`() = runTest {
        val item = CartItemEntity("1", "Product 1", 10.0, 1, "image")
        coEvery { dao.deleteItem(item) } just Runs

        repository.removeItem(item)

        coVerify { dao.deleteItem(item) }
    }

    @Test
    fun `clearCart should call dao clearCart`() = runTest {
        coEvery { dao.clearCart() } just Runs

        repository.clearCart()

        coVerify { dao.clearCart() }
    }

    @Test
    fun `increaseQuantity should call dao increaseQuantity`() = runTest {
        val item = CartItemEntity("1", "Product 1", 10.0, 1, "image")
        coEvery { dao.increaseQuantity(item.productId) } just Runs

        repository.increaseQuantity(item)

        coVerify { dao.increaseQuantity(item.productId) }
    }

    @Test
    fun `decreaseQuantity should call dao decreaseQuantity`() = runTest {
        val item = CartItemEntity("1", "Product 1", 10.0, 1, "image")
        coEvery { dao.decreaseQuantity(item.productId) } just Runs

        repository.decreaseQuantity(item)

        coVerify { dao.decreaseQuantity(item.productId) }
    }
}
