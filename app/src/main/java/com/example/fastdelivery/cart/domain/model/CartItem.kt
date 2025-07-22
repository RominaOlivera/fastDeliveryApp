package com.example.fastdelivery.cart.domain.model

data class CartItem(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    var quantity: Int
)
