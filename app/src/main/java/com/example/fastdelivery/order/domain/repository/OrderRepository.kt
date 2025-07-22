package com.example.fastdelivery.order.domain.repository

import com.example.fastdelivery.order.data.local.LocalOrderDao
import com.example.fastdelivery.order.data.remote.OrderApi
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.data.local.toApiOrder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderApi: OrderApi,
    private val localOrderDao: LocalOrderDao
) {
    fun getLocalOrders(userId: String) = localOrderDao.getOrdersForUser(userId)

    suspend fun placeOrder(order: LocalOrderEntity) {
        val orderApiModel = order.toApiOrder()
        val response = orderApi.placeOrder(orderApiModel)

        if (response.isSuccessful) {
            localOrderDao.insertOrder(order)
        } else {
            throw Exception("Failed to place order: ${response.code()}")
        }
    }
}
