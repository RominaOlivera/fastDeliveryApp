package com.example.fastdelivery.order.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastdelivery.order.presentation.component.OrderCard
import com.example.fastdelivery.profile.presentation.ProfileViewModel
import androidx.compose.foundation.lazy.items
import com.example.fastdelivery.order.presentation.viewmodel.OrderViewModel


@Composable
fun OrdersScreen(
    userEmail: String,
    onBack: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val user by profileViewModel.user.collectAsState()
    val orders by orderViewModel.orders.collectAsState()
    val errorMessage by orderViewModel.errorMessage.collectAsState()

    LaunchedEffect(userEmail) {
        profileViewModel.fetchUserByEmail(userEmail)
    }

    LaunchedEffect(user) {
        user?.id?.let { orderViewModel.fetchOrdersForUser(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                text = "My Orders",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Text("No orders yet", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn {
                items(orders) { order ->
                    OrderCard(order = order)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }
    }
}
