package com.example.fastdelivery.order.domain.model

data class OrderItem(
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val quantity: Int
)

data class Order(
    val orderId: String,
    val productIds: List<OrderItem>,
    val total: Double,
    val timestamp: Long,
    val userId: String
)
