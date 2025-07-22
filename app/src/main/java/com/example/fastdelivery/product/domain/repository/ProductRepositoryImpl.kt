package com.example.fastdelivery.product.domain.repository

import com.example.fastdelivery.product.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : ProductRepository {

    override fun getLocalProducts(): Flow<List<Product>> {
        return flowOf(emptyList())
    }

    override suspend fun syncProductWithServer(product: Product) {
    }
}
