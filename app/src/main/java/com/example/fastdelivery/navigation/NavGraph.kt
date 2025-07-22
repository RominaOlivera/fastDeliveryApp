package com.example.fastdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fastdelivery.auth.presentation.screen.CreateNewAccountScreen
import com.example.fastdelivery.auth.presentation.screen.LoginScreen
import com.example.fastdelivery.cart.presentation.viewmodel.CartViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate("createAccount")
                },
                onLoginSuccess = { email ->
                    navController.navigate("main/$email") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("createAccount") {
            CreateNewAccountScreen(
                onSignInClick = {
                    navController.popBackStack()
                },
                onSuccessNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("createAccount") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "main/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            MainScreenWithBottomBar(
                externalNavController = navController,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("main/$email") { inclusive = true }
                    }
                },
                userEmail = email,
                cartViewModel = cartViewModel
            )
        }
    }
}
