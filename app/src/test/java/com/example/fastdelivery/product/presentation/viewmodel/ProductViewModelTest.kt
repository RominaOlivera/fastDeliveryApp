package com.example.fastdelivery.product.presentation.viewmodel

import com.example.fastdelivery.product.domain.model.Product
import com.example.fastdelivery.product.domain.usecases.GetProductsUseCase
import com.example.fastdelivery.product.domain.usecases.SyncProductDataUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var syncProductDataUseCase: SyncProductDataUseCase
    private lateinit var viewModel: ProductViewModel

    private val sampleProducts = listOf(
        Product("1", "Beef burrito", "With rice, beans, beef and cheese.", "url", 60.0, false, "General"),
        Product("2", "Coke", "Cold drink", "url", 150.0, true, "Drink")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = mockk()
        syncProductDataUseCase = mockk(relaxed = true)
        coEvery { getProductsUseCase() } returns emptyList()
        viewModel = ProductViewModel(getProductsUseCase, syncProductDataUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts success updates products and clears error`() = runTest {
        coEvery { getProductsUseCase() } returns sampleProducts

        viewModel.fetchProducts()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(sampleProducts, viewModel.products.value)
        assertNull(viewModel.error.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchProducts failure updates error and stops loading`() = runTest {
        val errorMessage = "Network error"
        coEvery { getProductsUseCase() } throws Exception(errorMessage)

        viewModel.fetchProducts()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error loading products: $errorMessage", viewModel.error.value)
        assertTrue(viewModel.products.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }
}
