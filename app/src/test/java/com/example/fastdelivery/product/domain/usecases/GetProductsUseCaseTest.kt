package com.example.fastdelivery.product.domain.usecases

import com.example.fastdelivery.product.data.remote.ProductApi
import com.example.fastdelivery.product.domain.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    private lateinit var productApi: ProductApi
    private lateinit var getProductsUseCase: GetProductsUseCase

    private val sampleProducts = listOf(
        Product("1", "Beef burrito", "With rice, beans, beef and cheese.", "url", 60.0, false, "General"),
        Product("2", "Coke", "Cold drink", "url", 150.0, true, "Drink")
    )

    @Before
    fun setup() {
        productApi = mockk()
        getProductsUseCase = GetProductsUseCase(productApi)
    }

    @Test
    fun `invoke returns product list`() = runTest {
        coEvery { productApi.getFoods() } returns sampleProducts

        val result = getProductsUseCase()

        assertEquals(sampleProducts, result)
    }
}
