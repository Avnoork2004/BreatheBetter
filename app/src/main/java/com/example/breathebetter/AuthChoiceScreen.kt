package com.example.breathebetter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// AuthChoiceScreen lets you choose between logging in or registering
@Composable
fun AuthChoiceScreen(navController: NavController) {
    // Box fills the whole screen and centers everything inside
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Column arranges vertically with spacing
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // center horizontally
            verticalArrangement = Arrangement.spacedBy(16.dp) // space between items
        ) {

            // Welcome text at the top
            Text("Welcome to BreatheBetter", style = MaterialTheme.typography.headlineMedium)

            // Image below it
            Image(
                painter = painterResource(id = R.drawable.logo), // drawable resource for the logo
                contentDescription = "App Logo", //description
                modifier = Modifier
                    .size(150.dp) // size of the image
            )

            // Login button to login page
            Button(onClick = { navController.navigate("login") }) { Text("Login") }
            // register button to register page
            Button(onClick = { navController.navigate("register") }) { Text("Register") }
        }
    }
}