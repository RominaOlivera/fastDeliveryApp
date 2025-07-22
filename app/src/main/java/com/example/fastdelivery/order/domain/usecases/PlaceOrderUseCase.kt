package com.example.fastdelivery.order.domain.usecases

import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.domain.repository.OrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(order: LocalOrderEntity) {
        orderRepository.placeOrder(order)
    }
}
