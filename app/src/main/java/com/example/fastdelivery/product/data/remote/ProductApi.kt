package com.example.fastdelivery.product.data.remote

import com.example.fastdelivery.product.domain.model.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("foods")
    suspend fun getFoods(): List<Product>
}
