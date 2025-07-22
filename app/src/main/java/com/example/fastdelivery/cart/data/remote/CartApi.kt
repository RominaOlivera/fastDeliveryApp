package com.example.fastdelivery.cart.data.remote

import com.example.fastdelivery.product.domain.model.Product
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface CartApi {
    @GET("foods/{id}")
    suspend fun getProductById(@Path("id") productId: String): Response<Product>

    @POST("cart/add")
    suspend fun addProductToCart(@Body product: Product): Response<Void>

    @DELETE("cart/{id}")
    suspend fun deleteProductFromCart(@Path("id") productId: String): Response<Void>
}
