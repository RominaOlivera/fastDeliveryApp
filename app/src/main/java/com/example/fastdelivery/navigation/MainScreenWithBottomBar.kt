package com.example.fastdelivery.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.fastdelivery.cart.presentation.screen.CartScreen
import com.example.fastdelivery.cart.presentation.viewmodel.CartViewModel
import com.example.fastdelivery.components.BottomNavBar
import com.example.fastdelivery.home.presentation.screen.HomeScreen
import com.example.fastdelivery.order.presentation.screen.OrdersScreen
import com.example.fastdelivery.profile.presentation.ProfileViewModel
import com.example.fastdelivery.profile.presentation.screen.EditProfileScreen
import com.example.fastdelivery.profile.presentation.screen.ProfilePreviewScreen
import com.example.fastdelivery.profile.presentation.screen.ProfileScreen
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreenWithBottomBar(
    externalNavController: NavHostController,
    onLogout: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    userEmail: String,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()

    val cartItems by cartViewModel.cartItems.collectAsState()
    val user by profileViewModel.user.collectAsState()

    LaunchedEffect(userEmail) {
        profileViewModel.fetchUserByEmail(userEmail)
    }

    val fullName = user?.fullName ?: userEmail

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    if (route != currentRoute) bottomNavController.navigate(route)
                },
                cartItems = cartItems
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .imePadding()
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(cartViewModel = cartViewModel, username = fullName)
            }
            composable("profile") {
                ProfileScreen(
                    email = userEmail,
                    onLogout = onLogout,
                    navController = bottomNavController
                )
            }
            composable("cart") {
                CartScreen(
                    cartViewModel = cartViewModel,
                    userId = user?.id ?: "",
                    onBack = { bottomNavController.popBackStack() }
                )
            }
            composable("edit_profile") {
                EditProfileScreen(
                    userEmail = userEmail,
                    onBack = { bottomNavController.popBackStack() },
                    viewModel = profileViewModel
                )
            }
            composable("preview_profile") {
                ProfilePreviewScreen(
                    userEmail = userEmail,
                    viewModel = profileViewModel,
                    onBack = { bottomNavController.popBackStack() },
                    onEdit = { bottomNavController.navigate("edit_profile") }
                )
            }
            composable("orders") {
                OrdersScreen(
                    userEmail = userEmail,
                    onBack = { bottomNavController.popBackStack() }
                )
            }
        }
    }
}
