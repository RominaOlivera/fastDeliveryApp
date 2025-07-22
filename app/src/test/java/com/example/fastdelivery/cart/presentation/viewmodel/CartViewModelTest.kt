package com.example.fastdelivery.cart.presentation.viewmodel

import app.cash.turbine.test
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.repository.CartRepository
import com.example.fastdelivery.cart.domain.usecases.AddCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.ClearCartUseCase
import com.example.fastdelivery.cart.domain.usecases.DecreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.IncreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.RemoveCartItemUseCase
import com.example.fastdelivery.order.domain.repository.OrderRepository
import com.example.fastdelivery.utils.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals


@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val addCartItemUseCase: AddCartItemUseCase = mockk(relaxed = true)
    private val removeCartItemUseCase: RemoveCartItemUseCase = mockk(relaxed = true)
    private val clearCartUseCase: ClearCartUseCase = mockk(relaxed = true)
    private val increaseCartItemUseCase: IncreaseCartItemUseCase = mockk(relaxed = true)
    private val decreaseCartItemUseCase: DecreaseCartItemUseCase = mockk(relaxed = true)
    private val orderRepository: OrderRepository = mockk(relaxed = true)

    private val repository: CartRepository = mockk(relaxed = true)

    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        every { repository.cartItems } returns MutableStateFlow(emptyList())
        viewModel = CartViewModel(
            addCartItemUseCase,
            removeCartItemUseCase,
            clearCartUseCase,
            increaseCartItemUseCase,
            decreaseCartItemUseCase,
            orderRepository,
            repository
        )
    }

    @Test
    fun `addItem should call addCartItemUseCase`() = runTest {
        val item = CartItemEntity("1", "Test", 10.0, 1, "image")

        viewModel.addItem(item)

        coVerify { addCartItemUseCase(item) }
    }

    @Test
    fun `removeItem should call removeCartItemUseCase`() = runTest {
        val item = CartItemEntity("1", "Test", 10.0, 1, "image")

        viewModel.removeItem(item)

        coVerify { removeCartItemUseCase(item) }
    }

    @Test
    fun `clearCart should call clearCartUseCase`() = runTest {
        viewModel.clearCartAction()

        coVerify { clearCartUseCase() }
    }

    @Test
    fun `increaseQuantity should call increaseCartItemUseCase`() = runTest {
        val item = CartItemEntity("1", "Test", 10.0, 1, "image")

        viewModel.increaseQuantity(item)

        coVerify { increaseCartItemUseCase(item) }
    }

    @Test
    fun `decreaseQuantity should call decreaseCartItemUseCase`() = runTest {
        val item = CartItemEntity("1", "Test", 10.0, 1, "image")

        viewModel.decreaseQuantity(item)

        coVerify { decreaseCartItemUseCase(item) }
    }

    @Test
    fun `cartItems emits from repository`() = runTest {
        val items = listOf(CartItemEntity("1", "Test", 10.0, 1, "image"))
        val flow = MutableStateFlow(emptyList<CartItemEntity>())

        every { repository.cartItems } returns flow

        viewModel = CartViewModel(
            addCartItemUseCase,
            removeCartItemUseCase,
            clearCartUseCase,
            increaseCartItemUseCase,
            decreaseCartItemUseCase,
            orderRepository,
            repository
        )

        viewModel.cartItems.test {
            assertEquals(emptyList<CartItemEntity>(), awaitItem())

            flow.value = items
            assertEquals(items, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
