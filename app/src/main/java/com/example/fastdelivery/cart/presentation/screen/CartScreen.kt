package com.example.fastdelivery.cart.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastdelivery.cart.presentation.component.CartItemList
import com.example.fastdelivery.cart.data.local.mapper.toEntity
import com.example.fastdelivery.cart.data.local.mapper.toModelList
import com.example.fastdelivery.cart.presentation.viewmodel.CartViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    userId: String,
    onBack: () -> Unit
) {
    val cartEntities by cartViewModel.cartItems.collectAsState()
    val cartItems = cartEntities.toModelList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (cartItems.isEmpty()) {
                EmptyCartView()
            } else {
                CartItemList(
                    cartItems = cartItems,
                    onIncrease = { item -> cartViewModel.increaseQuantity(item.toEntity()) },
                    onDecrease = { item -> cartViewModel.decreaseQuantity(item.toEntity()) },
                    onRemove = { item -> cartViewModel.removeItem(item.toEntity()) }
                )
            }
        }

        if (cartItems.isNotEmpty()) {
            PaymentSummary(
                cartItems = cartItems,
                onClearCart = { cartViewModel.clearCartAction() },
                userId = userId,
                onPlaceOrder = { order -> cartViewModel.placeOrder(order) }
            )
        }
    }
}
