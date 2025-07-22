package com.example.fastdelivery.auth.presentation.screen

import CustomButton
import TextWithAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastdelivery.auth.utils.isEmailValid
import com.example.fastdelivery.auth.utils.isPasswordValid
import com.example.fastdelivery.auth.presentation.viewmodel.AuthViewModel
import com.example.fastdelivery.components.*

@Composable
fun CreateNewAccountScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onSuccessNavigateToLogin: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val fullName = remember { mutableStateOf("") }
    val encryptedPassword = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }

    val state = authViewModel.registerState.collectAsState()

    if (state.value?.startsWith("Success") == true) {
        showDialog.value = true
    }

    if (showDialog.value) {
        SuccessDialog(
            title = "User Registered",
            message = "Your account was successfully created.",
            buttonText = "Sign In",
            onDismiss = {
                showDialog.value = false
                authViewModel.clearRegisterState()
                onSuccessNavigateToLogin()
            }
        )
    }

    if (state.value?.startsWith("Error") == true) {
        ErrorDialog(
            title = "Registration Failed",
            message = state.value ?: "An error occurred",
            buttonText = "Close",
            onDismiss = {
                authViewModel.clearRegisterState()
            }
        )
    }


    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        Text(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp),
            text = "Create your new account",
            style = TextStyle(fontSize = 45.sp, fontWeight = FontWeight(600))
        )
        Text(
            text = "Create an account to start looking for the food you like",
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp),
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Email Address",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        CustomTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = null
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        emailError.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(start = 24.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "User Name",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        CustomTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
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
            onValueChange = {
                encryptedPassword.value = it
                passwordError.value = null
            },
            isPassword = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        passwordError.value?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(start = 24.dp, top = 4.dp)
            )
        }

        CustomButton(
            text = "Register",
            onClick = {
                val emailValid = isEmailValid(email.value)
                val passwordValid = isPasswordValid(encryptedPassword.value)

                emailError.value = if (!emailValid) "Invalid email address" else null
                passwordError.value = if (!passwordValid) "Password must be at least 8 characters" else null

                if (!emailValid || !passwordValid) return@CustomButton

                authViewModel.register(
                    email = email.value,
                    fullName = fullName.value,
                    encryptedPassword = encryptedPassword.value
                )
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
        )


        TextWithAction(
            normalText = "Already have an account?",
            actionText = "Sign In",
            onActionClick = onSignInClick,
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
