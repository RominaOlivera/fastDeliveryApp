package com.example.fastdelivery.cart.data.local.mapper

import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.model.CartItem

fun CartItemEntity.toModel(): CartItem {
    return CartItem(
        id = this.productId,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        image = this.image,
        description = "",
        imageUrl = this.image,
        hasDrink = false
    )
}


fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        image = this.image
    )
}

fun List<CartItemEntity>.toModelList(): List<CartItem> {
    return this.map { it.toModel() }
}

fun List<CartItem>.toEntityList(): List<CartItemEntity> {
    return this.map { it.toEntity() }
}
