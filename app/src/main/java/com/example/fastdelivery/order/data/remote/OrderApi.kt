package com.example.fastdelivery.order.data.remote

import com.example.fastdelivery.order.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface OrderApi {
    @POST("orders")
    suspend fun placeOrder(@Body order: Order): Response<Unit>

    @GET("orders")
    suspend fun getOrders(): List<Order>
}
