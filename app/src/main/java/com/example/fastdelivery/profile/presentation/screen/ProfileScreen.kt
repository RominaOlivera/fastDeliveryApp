package com.example.fastdelivery.profile.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fastdelivery.profile.presentation.ProfileViewModel
import com.example.fastdelivery.profile.presentation.component.ProfileHeader
import android.net.Uri


@Composable
fun ProfileScreen(
    email: String,
    onLogout: () -> Unit,
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(email) {
        viewModel.fetchUserByEmail(email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileHeader(
            fullName = user?.fullName,
            email = user?.email,
            onBack = { navController.popBackStack() },
            title = "Profile Settings",
            showUserInfo = true,
            imageUri = user?.imageUrl?.let { Uri.parse(it) },
            showEditIcon = false
        )

        ListItem(
            headlineContent = { Text("Personal Data") },
            leadingContent = {
                Icon(Icons.Default.Person, contentDescription = "Personal Data")
            },
            trailingContent = {
                Icon(Icons.Default.ArrowForward, contentDescription = "Go")
            },
            modifier = Modifier.clickable { navController.navigate("preview_profile") }
        )

        ListItem(
            headlineContent = { Text("My orders") },
            leadingContent = {
                Icon(Icons.Default.List, contentDescription = "Orders")
            },
            trailingContent = {
                Icon(Icons.Default.ArrowForward, contentDescription = "Go")
            },
            modifier = Modifier.clickable { navController.navigate("orders") }
        )

        Spacer(modifier = Modifier.weight(1f))

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign Out", color = Color.Red)
        }
    }
}
