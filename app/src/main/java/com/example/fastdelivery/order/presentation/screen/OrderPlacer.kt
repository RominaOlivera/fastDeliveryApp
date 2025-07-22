package com.example.fastdelivery.order.presentation.screen


import com.example.fastdelivery.order.data.local.LocalOrderEntity
import android.util.Log
import com.example.fastdelivery.cart.domain.model.CartItem
import com.example.fastdelivery.order.domain.model.OrderItem
import com.example.fastdelivery.order.domain.repository.OrderRepository
import java.util.*

suspend fun placeOrderFromCart(
    cartItems: List<CartItem>,
    orderRepository: OrderRepository,
    userId: String
) {
    val orderItems = cartItems.map {
        OrderItem(
            name = it.name,
            description = "No description",
            imageUrl = it.imageUrl,
            price = it.price,
            hasDrink = false,
            quantity = it.quantity
        )
    }

    val totalPrice = cartItems.sumOf { it.quantity * it.price }

    val localOrder = LocalOrderEntity(
        orderId = UUID.randomUUID().toString(),
        userId = userId,
        total = totalPrice,
        timestamp = System.currentTimeMillis(),
        productIds = orderItems
    )

    try {
        orderRepository.placeOrder(localOrder)
        Log.d("Order", "Order placed successfully")
    } catch (e: Exception) {
        Log.e("Order", "Failed to place order: ${e.message}")
    }
}
