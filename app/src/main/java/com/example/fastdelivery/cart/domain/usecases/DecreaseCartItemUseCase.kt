package com.example.fastdelivery.cart.domain.usecases

import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.repository.CartRepository
import javax.inject.Inject

class DecreaseCartItemUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItemEntity) {
        repository.decreaseQuantity(item)
    }
}
