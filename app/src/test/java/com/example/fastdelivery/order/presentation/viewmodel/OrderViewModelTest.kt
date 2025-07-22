package com.example.fastdelivery.order.presentation.viewmodel

import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.data.local.toOrder
import com.example.fastdelivery.order.domain.model.OrderItem
import com.example.fastdelivery.order.domain.usecases.GetOrdersForUserUseCase
import com.example.fastdelivery.order.domain.usecases.PlaceOrderUseCase
import com.example.fastdelivery.order.domain.usecases.SyncOrdersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var placeOrderUseCase: PlaceOrderUseCase
    private lateinit var getOrdersForUserUseCase: GetOrdersForUserUseCase
    private lateinit var syncOrdersUseCase: SyncOrdersUseCase
    private lateinit var viewModel: OrderViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        placeOrderUseCase = mockk()
        getOrdersForUserUseCase = mockk()
        syncOrdersUseCase = mockk(relaxed = true)
        viewModel = OrderViewModel(placeOrderUseCase, getOrdersForUserUseCase, syncOrdersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `placeOrder success clears errorMessage`() = runTest {
        val productList = listOf(
            OrderItem(
                name = "Pizza",
                description = "Muzzarella",
                imageUrl = "https://example.com/pizza.png",
                price = 400.0,
                hasDrink = false,
                quantity = 1
            )
        )

        val order = LocalOrderEntity(
            orderId = "order123",
            userId = "user123",
            productIds = productList,
            total = 400.0,
            timestamp = System.currentTimeMillis()
        )

        coEvery { placeOrderUseCase(order) } returns Unit

        viewModel.placeOrder(order)
        advanceUntilIdle()

        assertEquals(null, viewModel.errorMessage.value)
        coVerify(exactly = 1) { placeOrderUseCase(order) }
    }

    @Test
    fun `fetchOrdersForUser success updates orders`() = runTest {
        val userId = "user123"

        val productList = listOf(
            OrderItem(
                name = "Pizza",
                description = "Muzzarella",
                imageUrl = "https://example.com/pizza.png",
                price = 400.0,
                hasDrink = false,
                quantity = 1
            )
        )

        val localOrders = listOf(
            LocalOrderEntity(
                orderId = "order123",
                userId = userId,
                productIds = productList,
                total = 400.0,
                timestamp = System.currentTimeMillis()
            )
        )

        coEvery { getOrdersForUserUseCase(userId) } returns flowOf(localOrders)

        viewModel.fetchOrdersForUser(userId)
        advanceUntilIdle()

        assertEquals(localOrders.map { it.toOrder() }, viewModel.orders.value)
    }

    @Test
    fun `startOrderSync calls execute on syncOrdersUseCase`() = runTest {
        val userId = "user123"
        viewModel.startOrderSync(userId)
        advanceUntilIdle()
        coVerify(exactly = 1) { syncOrdersUseCase.execute(userId) }
    }
}
