package com.example.fastdelivery.home.presentation.screen

import CategorySection
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastdelivery.R
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.presentation.viewmodel.CartViewModel
import com.example.fastdelivery.home.presentation.component.HeaderSection
import com.example.fastdelivery.product.presentation.screen.ProductGrid
import com.example.fastdelivery.product.presentation.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    cartViewModel: CartViewModel,
    viewModel: ProductViewModel = hiltViewModel(),
    username: String
) {
    val products by viewModel.filteredProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    val onCloseSearch = {
        viewModel.updateSearchQuery("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        HeaderSection(
            username = username,
            searchQuery = searchQuery,
            onSearchQueryChanged = { viewModel.updateSearchQuery(it) },
            onCloseSearch = onCloseSearch
        )
        CategorySection(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error loading products: $error",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    if (products.isEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_results),
                                contentDescription = "No results",
                                modifier = Modifier.size(150.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "We couldn't find any result!",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        ProductGrid(
                            products = products,
                            onAddToCart = { product ->
                                try {
                                    cartViewModel.addItem(
                                        CartItemEntity(
                                            productId = product.id,
                                            name = product.name,
                                            price = product.price,
                                            quantity = 1,
                                            image = product.imageUrl
                                        )
                                    )
                                } catch (e: Exception) {
                                    Log.e("HomeScreen", "Error adding to cart: ${e.message}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
