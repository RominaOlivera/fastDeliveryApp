package com.example.fastdelivery.profile.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fastdelivery.profile.presentation.ProfileViewModel
import com.example.fastdelivery.profile.presentation.component.ProfileHeader
import com.example.fastdelivery.profile.presentation.component.ProfilePreviewCard

@Composable
fun ProfilePreviewScreen(
    userEmail: String,
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {

    val user by viewModel.user.collectAsState()

    LaunchedEffect(userEmail) {
        viewModel.fetchUserByEmail(userEmail)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileHeader(
            fullName = user?.fullName,
            email = user?.email,
            onBack = onBack,
            title = "Profile Preview",
            showUserInfo = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        user?.let {
            ProfilePreviewCard(
                name = it.fullName,
                email = it.email,
                nationality = it.nationality ?: "Not defined",
                imageUrl = it.imageUrl,
                onEdit = onEdit
            )
        }
    }
}