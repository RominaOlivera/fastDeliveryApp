package com.example.fastdelivery.order.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fastdelivery.order.domain.model.Order
import com.example.fastdelivery.order.domain.model.OrderItem

@Entity
data class LocalOrderEntity(
    @PrimaryKey val orderId: String,
    val userId: String,
    val productIds: List<OrderItem>,
    val total: Double,
    val timestamp: Long
)
fun LocalOrderEntity.toOrder(): Order {
    return Order(
        orderId = this.orderId,
        userId = this.userId,
        productIds = this.productIds,
        total = this.total,
        timestamp = this.timestamp
    )
}

fun LocalOrderEntity.toApiOrder(): Order {
    return Order(
        orderId = this.orderId,
        userId = this.userId,
        productIds = this.productIds,
        total = this.total,
        timestamp = this.timestamp
    )
}