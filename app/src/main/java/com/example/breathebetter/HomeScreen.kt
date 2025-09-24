package com.example.breathebetter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// homescreen is shown after login is successful
@Composable
fun HomeScreen(navController: NavController) {
    // Column puts everything vertically
    // Fills the whole screen and I added padding
    Column(
        modifier = Modifier
            .fillMaxSize() //takes the width and height
            .padding(16.dp), //adds padding around
        verticalArrangement = Arrangement.Center, //centers vertically
        horizontalAlignment = Alignment.CenterHorizontally //centers horizontally
    ) {
        //welcome text
        Text("Welcome to the Home Page!", style = MaterialTheme.typography.headlineMedium)
        //adds space between
        Spacer(modifier = Modifier.height(16.dp))

        //logout btn goes back to the "welcome" page with login/register btn
        Button(onClick = {
            navController.navigate("auth")
        }) {
            Text("Logout")
        }
    }
}