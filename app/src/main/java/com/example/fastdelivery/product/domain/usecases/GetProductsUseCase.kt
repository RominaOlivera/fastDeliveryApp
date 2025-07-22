package com.example.fastdelivery.product.domain.usecases

import com.example.fastdelivery.product.data.remote.ProductApi
import com.example.fastdelivery.product.domain.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productApi: ProductApi
) {
    suspend operator fun invoke(): List<Product> {
        return productApi.getFoods()
    }
}
