package com.example.fastdelivery.order.domain.usecases

import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.domain.model.OrderItem
import com.example.fastdelivery.order.domain.repository.OrderRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetOrdersForUserUseCaseTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var getOrdersForUserUseCase: GetOrdersForUserUseCase

    @Before
    fun setUp() {
        orderRepository = mockk()
        getOrdersForUserUseCase = GetOrdersForUserUseCase(orderRepository)
    }

    @Test
    fun `invoke should return flow of orders from repository`() = runTest {
        val userId = "user123"

        val productList = listOf(
            OrderItem(
                name = "Hamburguesa",
                description = "Deliciosa hamburguesa doble queso",
                imageUrl = "https://example.com/hamburguesa.png",
                price = 250.0,
                hasDrink = true,
                quantity = 2
            )
        )

        val orders = listOf(
            LocalOrderEntity(
                orderId = "order123",
                userId = userId,
                productIds = productList,
                total = 500.0,
                timestamp = System.currentTimeMillis()
            )
        )

        every { orderRepository.getLocalOrders(userId) } returns flowOf(orders)

        val result = getOrdersForUserUseCase(userId)

        result.collect {
            assertEquals(orders, it)
        }
    }
}
