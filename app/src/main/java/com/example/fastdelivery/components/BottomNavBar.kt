package com.example.fastdelivery.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.zIndex
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.navigation.BottomNavItem

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    cartItems: List<CartItemEntity>
) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart"),
        BottomNavItem("profile", Icons.Default.Person, "Profile")
    )

    val totalQuantity = cartItems.sumOf { it.quantity }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = when (item.route) {
                "profile" -> currentRoute == "profile" ||
                        currentRoute == "edit_profile" ||
                        currentRoute == "preview_profile" ||
                        currentRoute == "orders"
                else -> currentRoute == item.route
            }
            val activeColor = if (selected) Color(0xFFED751C) else Color.Gray

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onNavigate(item.route) }
                    .padding(vertical = 8.dp)
                    .sizeIn(minWidth = 64.dp, minHeight = 48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = activeColor,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (item.route == "cart" && totalQuantity > 0) {
                        Box(
                            modifier = Modifier
                                .offset(x = 8.dp, y = (-4).dp)
                                .background(Color(0xFFED751C), shape = CircleShape)
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                                .defaultMinSize(minWidth = 18.dp, minHeight = 18.dp)
                                .zIndex(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = totalQuantity.toString(),
                                color = Color.White,
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }
                }

                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    color = activeColor,
                    maxLines = 1,
                )
            }
        }
    }
}
