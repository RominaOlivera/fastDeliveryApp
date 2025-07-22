package com.example.fastdelivery.order.domain.usecases

import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.domain.model.OrderItem
import com.example.fastdelivery.order.domain.repository.OrderRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlaceOrderUseCaseTest {

    private lateinit var orderRepository: OrderRepository
    private lateinit var placeOrderUseCase: PlaceOrderUseCase

    @Before
    fun setUp() {
        orderRepository = mockk()
        placeOrderUseCase = PlaceOrderUseCase(orderRepository)
    }

    @Test
    fun `invoke should call repository placeOrder`() = runTest {
        val productList = listOf(
            OrderItem(
                name = "Hamburguesa",
                description = "Deliciosa hamburguesa doble queso",
                imageUrl = "https://example.com/hamburguesa.png",
                price = 250.0,
                hasDrink = true,
                quantity = 2
            ),
            OrderItem(
                name = "Papas fritas",
                description = "Papas crujientes con salsa",
                imageUrl = "https://example.com/papas.png",
                price = 100.0,
                hasDrink = false,
                quantity = 1
            )
        )

        val order = LocalOrderEntity(
            orderId = "order123",
            userId = "user123",
            productIds = productList,
            total = productList.sumOf { it.price * it.quantity },
            timestamp = System.currentTimeMillis()
        )


        coEvery { orderRepository.placeOrder(order) } returns Unit

        placeOrderUseCase.invoke(order)

        coVerify(exactly = 1) { orderRepository.placeOrder(order) }
    }
}
