package com.example.fastdelivery.cart.domain.repository

import com.example.fastdelivery.cart.data.local.CartItemDao
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val dao: CartItemDao
) {

    val cartItems: Flow<List<CartItemEntity>> = dao.getCartItems()

    suspend fun addItem(item: CartItemEntity) {
        val existingItem = dao.getItemByProductId(item.productId)
        if (existingItem != null) {
            dao.increaseQuantity(item.productId)
        } else {
            dao.insertItem(item)
        }
    }

    suspend fun removeItem(item: CartItemEntity) = dao.deleteItem(item)

    suspend fun clearCart() = dao.clearCart()

    suspend fun increaseQuantity(item: CartItemEntity) {
        dao.increaseQuantity(item.productId)
    }

    suspend fun decreaseQuantity(item: CartItemEntity) {
        dao.decreaseQuantity(item.productId)
    }
}

