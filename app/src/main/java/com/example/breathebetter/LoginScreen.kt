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
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.breathebetter.viewmodel.AuthViewModel

// login screen with email & password and then goes to Home screen
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf("") }

    val authError by authViewModel.authError.collectAsState(initial = null)
    val currentUser by authViewModel.currentUserId.collectAsState(initial = null)

    LaunchedEffect(currentUser) {
        currentUser?.let {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Center content vertically and horizontally
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
                .fillMaxWidth(0.9f), // Optional: limits width for better centering
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text("Welcome to BreatheBetter", style = MaterialTheme.typography.headlineMedium)


            Spacer(modifier = Modifier.height(16.dp))

            // Centered logo/image
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Login", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (localError.isNotEmpty()) {
                Text(localError, color = MaterialTheme.colorScheme.error)
            }

            if (!authError.isNullOrEmpty()) {
                Text(authError!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    localError = when {
                        email.isEmpty() -> "Email cannot be empty"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
                        password.isEmpty() -> "Password cannot be empty"
                        password.length < 6 -> "Password must be at least 6 characters"
                        else -> ""
                    }
                    if (localError.isEmpty()) authViewModel.login(email.trim(), password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}