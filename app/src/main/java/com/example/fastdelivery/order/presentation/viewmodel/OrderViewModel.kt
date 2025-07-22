package com.example.fastdelivery.order.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.data.local.toOrder
import com.example.fastdelivery.order.domain.model.Order
import com.example.fastdelivery.order.domain.usecases.GetOrdersForUserUseCase
import com.example.fastdelivery.order.domain.usecases.PlaceOrderUseCase
import com.example.fastdelivery.order.domain.usecases.SyncOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val getOrdersForUserUseCase: GetOrdersForUserUseCase,
    private val syncOrdersUseCase: SyncOrdersUseCase
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun startOrderSync(userId: String) {

        Log.d("OrderViewModel", "startOrderSync() DISPARADO con userId: $userId")
        syncOrdersUseCase.execute(userId)
    }

    fun placeOrder(order: LocalOrderEntity) {
        viewModelScope.launch {
            try {
                placeOrderUseCase(order)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error placing order: ${e.localizedMessage}"
            }
        }
    }

    fun fetchOrdersForUser(userId: String) {
        viewModelScope.launch {
            try {
                getOrdersForUserUseCase(userId).collect { localOrders ->
                    _orders.value = localOrders.map { it.toOrder() }
                }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load orders: ${e.localizedMessage}"
            }
        }
    }
}
