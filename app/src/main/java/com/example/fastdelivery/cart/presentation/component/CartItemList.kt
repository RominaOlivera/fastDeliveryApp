package com.example.fastdelivery.cart.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.fastdelivery.cart.domain.model.CartItem

@Composable
fun CartItemList(
    cartItems: List<CartItem>,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
    onRemove: (CartItem) -> Unit
) {
    LazyColumn {
        items(cartItems) { item ->
            CartItemCard(
                item = item,
                onIncrease = onIncrease,
                onDecrease = onDecrease,
                onRemove = onRemove
            )
        }
    }
}
