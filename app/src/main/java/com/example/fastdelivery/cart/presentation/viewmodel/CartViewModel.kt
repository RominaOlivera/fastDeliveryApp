package com.example.fastdelivery.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.repository.CartRepository
import com.example.fastdelivery.cart.domain.usecases.AddCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.ClearCartUseCase
import com.example.fastdelivery.cart.domain.usecases.DecreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.IncreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.RemoveCartItemUseCase
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addCartItem: AddCartItemUseCase,
    private val removeCartItem: RemoveCartItemUseCase,
    private val clearCart: ClearCartUseCase,
    private val increaseCartItem: IncreaseCartItemUseCase,
    private val decreaseCartItem: DecreaseCartItemUseCase,
    private val orderRepository: OrderRepository,
    private val repository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addItem(item: CartItemEntity) = viewModelScope.launch {
        addCartItem(item)
    }

    fun removeItem(item: CartItemEntity) = viewModelScope.launch {
        removeCartItem(item)
    }

    fun clearCartAction() = viewModelScope.launch {
        clearCart()
    }

    fun increaseQuantity(item: CartItemEntity) = viewModelScope.launch {
        increaseCartItem(item)
    }

    fun decreaseQuantity(item: CartItemEntity) = viewModelScope.launch {
        decreaseCartItem(item)
    }

    fun placeOrder(order: LocalOrderEntity) = viewModelScope.launch {
        orderRepository.placeOrder(order)
    }
}
