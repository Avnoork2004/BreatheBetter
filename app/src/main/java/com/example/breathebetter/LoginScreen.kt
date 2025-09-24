package com.example.breathebetter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Patterns

// login screen with email & password and then goes to Home screen
@Composable
fun LoginScreen(navController: NavController) {
    // variables for email, password, and error messages
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    // Scrolls vertically if screen size is small
    val scrollState = rememberScrollState()

    // Outer Box takes full screen size
    Box(modifier = Modifier.fillMaxSize()) {
        // Column puts elements vertically with spacing
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) //scrolling on smaller screens
                .padding(16.dp), //padding on edges
            verticalArrangement = Arrangement.spacedBy(8.dp) //spacing between items
        ) {
            //title header login
            Text("Login", style = MaterialTheme.typography.headlineMedium)

            // Email with validation
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password with validation
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Shows error messages
            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = MaterialTheme.colorScheme.error)
            }

            // Login button
            Button(onClick = {
                errorMsg = when {
                    email.isEmpty() -> "Email cannot be empty"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email"
                    password.isEmpty() -> "Password cannot be empty"
                    password.length < 6 -> "Password must be at least 6 characters"
                    else -> ""
                }
                if (errorMsg.isEmpty()) navController.navigate("home") // if no errors go to home screen
            }) {
                Text("Login")
            }
        }
    }
}