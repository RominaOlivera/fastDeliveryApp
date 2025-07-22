package com.example.fastdelivery.auth.presentation.screen

import CustomButton
import TextWithAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastdelivery.auth.presentation.viewmodel.AuthViewModel
import com.example.fastdelivery.components.CustomTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit,
    onLoginSuccess: (Any?) -> Unit
){
    val email = remember { mutableStateOf("") }
    val encryptedPassword = remember { mutableStateOf("") }
    val loginState by authViewModel.loginState.collectAsState()
    val hasNavigated = remember { mutableStateOf(false) }
    if (loginState == "Success: User authenticated" && !hasNavigated.value) {
        hasNavigated.value = true
        onLoginSuccess(email.value)
        authViewModel.clearLoginState()
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
Text(
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp),
    text ="Login to your account.",
    style = TextStyle(
            fontSize = 45.sp,
            fontWeight = FontWeight(600)
        )
)
        Text(
            text = "Please sign in to your account",
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Email Address",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        CustomTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        CustomTextField(
            value = encryptedPassword.value,
            onValueChange = { encryptedPassword.value = it },
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        CustomButton(
            text = "Sign In",
            onClick = {
                authViewModel.login(email.value, encryptedPassword.value)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextWithAction(
            normalText = "Don't have an account?",
            actionText = "Register",
            onActionClick = onRegisterClick,
            modifier = Modifier.padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
        )
        loginState?.let { state ->
            val isError = state.startsWith("Error")
            val color = if (isError) Color.Red else Color.Green

            Text(
                text = state,
                color = color,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
        }

    }
}
