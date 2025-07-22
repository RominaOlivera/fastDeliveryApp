package com.example.fastdelivery.order.domain.usecases

import com.example.fastdelivery.order.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrdersForUserUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(userId: String) = orderRepository.getLocalOrders(userId)
}
