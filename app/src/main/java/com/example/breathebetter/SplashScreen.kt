package com.example.breathebetter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// SplashScreen is the first screen when the app launches
// showing the app logo and name then
// going to the authentication choice screen with login/register btn
@Composable
fun SplashScreen(navController: NavController) {
    // Box fills the screen and centers everything
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Column puts logo and text vertically centered
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //splash logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(140.dp)
            )
            //spacing between logo and app
            Spacer(modifier = Modifier.height(12.dp))
            //app name and text
            Text("BreatheBetter", fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }

    //  navigates back after delay
    LaunchedEffect(Unit) {
        delay(1700)
        navController.navigate("auth") {
            popUpTo("splash") { inclusive = true }
        }
    }
}