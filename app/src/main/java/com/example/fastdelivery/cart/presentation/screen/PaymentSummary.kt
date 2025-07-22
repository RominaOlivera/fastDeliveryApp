package com.example.fastdelivery.cart.presentation.screen

import CustomButton
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastdelivery.cart.domain.model.CartItem
import com.example.fastdelivery.components.SuccessDialog
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.domain.model.OrderItem
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun PaymentSummary(
    cartItems: List<CartItem>,
    onClearCart: () -> Unit,
    userId: String,
    onPlaceOrder: (LocalOrderEntity) -> Unit,
) {
    val totalItems = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.quantity * it.price }
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Payment Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Items ($totalItems)")
            Text("$${"%.2f".format(totalPrice)}")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Delivery Fee")
            Text("Free", color = Color(0xFF27AE60))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text(
                "$${"%.2f".format(totalPrice)}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE67E22)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Order Now",
            onClick = {
                coroutineScope.launch {
                    val localOrder = LocalOrderEntity(
                        orderId = UUID.randomUUID().toString(),
                        userId = userId,
                        productIds = cartItems.map {
                            OrderItem(
                                name = it.name,
                                description = "No description",
                                imageUrl = it.imageUrl,
                                price = it.price,
                                hasDrink = false,
                                quantity = it.quantity
                            )
                        },
                        total = totalPrice,
                        timestamp = System.currentTimeMillis()
                    )

                    onPlaceOrder(localOrder)
                    showDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDialog) {
        SuccessDialog(
            title = "Order Sent!",
            message = "Your order has been successfully placed.",
            buttonText = "OK",
            onDismiss = {
                showDialog = false
                onClearCart()
            }
        )
    }
}
