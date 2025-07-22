package com.example.fastdelivery.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastdelivery.product.domain.usecases.GetProductsUseCase
import com.example.fastdelivery.product.domain.usecases.SyncProductDataUseCase
import com.example.fastdelivery.product.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val syncProductDataUseCase: SyncProductDataUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    fun updateSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }

    val filteredProducts: StateFlow<List<Product>> = combine(
        _products, _searchQuery, _selectedCategory
    ) { products, query, selectedCategory ->

        var filtered = products

        if (!selectedCategory.isNullOrBlank()) {
            filtered = filtered.filter { product ->
                product.category.equals(selectedCategory, ignoreCase = true)
            }
        }

        if (query.isNotBlank()) {
            filtered = filtered.filter { product ->
                product.name.contains(query, ignoreCase = true)
            }
        }

        filtered
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        startPeriodicSync()
        fetchProducts()
    }

    private fun startPeriodicSync() {
        syncProductDataUseCase.executePeriodic()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = getProductsUseCase()
                _products.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error loading products: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
