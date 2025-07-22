package com.example.fastdelivery.product.domain.repository

import com.example.fastdelivery.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getLocalProducts(): Flow<List<Product>>
    suspend fun syncProductWithServer(product: Product)
}
