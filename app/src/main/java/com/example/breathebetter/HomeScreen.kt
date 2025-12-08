package com.example.breathebetter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.breathebetter.viewmodel.AuthViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// -------------------
// Reusable Tip Animation Button
// -------------------
@Composable
fun TipAnimationButton(
    buttonText: String,
    expandedText: String
) {
    var expanded by remember { mutableStateOf(false) }

    val bgColor by animateColorAsState(
        targetValue = if (expanded) Color(0xFFFFEBEE) else Color(0xFFFFF3F4),
        animationSpec = tween(durationMillis = 1200),
        label = "colorAnimation"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF967BB6),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
        ) {
            Text(text = buttonText, fontSize = 16.sp)
        }

        AnimatedVisibility(visible = expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(130.dp)
                    .padding(top = 6.dp)
                    .background(bgColor)
            ) {
                Text(
                    text = expandedText,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

// -------------------
// MAIN HOME SCREEN
// -------------------
@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {

    val userId by authViewModel.currentUserId.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Logo + Welcome Text + Logout Emoji
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )

                Text(
                    text = "Welcome to BreatheBetter",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Logout emoji button
            Text(
                text = "\uD83D\uDD12", // 🔒 emoji
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        authViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // -------------------
        // Centered Section Title
        // -------------------
        Text(
            text = "Tips & Breathing Exercises:",
            style = MaterialTheme.typography.headlineMedium,

            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // -------------------
        // Centered Tip Buttons
        // -------------------
        TipAnimationButton(
            buttonText = "\uD83C\uDF38 Take 5 deep breaths",
            expandedText = "Inhale 4s • Hold 1 • Exhale 6s\nRepeat 5× to calm your body."
        )

        Spacer(modifier = Modifier.height(40.dp))

        TipAnimationButton(
            buttonText = "\uD83C\uDF38 Use the 4-7-8 technique",
            expandedText = "Inhale 4s • Hold 7s • Exhale 8s.\nLowers stress and heart rate."
        )

        Spacer(modifier = Modifier.height(40.dp))

        TipAnimationButton(
            buttonText = "\uD83C\uDF38 Pause & notice surroundings",
            expandedText = "Name:\n• 3 things you see\n• 2 things you hear\n• 1 thing you feel"
        )

        Spacer(modifier = Modifier.height(40.dp))

        TipAnimationButton(
            buttonText = "\uD83C\uDF38 Mindful breathing",
            expandedText = "Close your eyes and breathe slowly.\nFocus on air moving in and out."
        )

        Spacer(modifier = Modifier.height(40.dp))

        TipAnimationButton(
            buttonText = "\uD83C\uDF38 Stretch shoulders & neck",
            expandedText = "Roll shoulders, release tension.\nBreathe steady and slow."
        )

        Spacer(modifier = Modifier.height(40.dp))

        // -------------------
        // Emoji Navigation Bar at Bottom
        // -------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\u2302", // 🏠 Home emoji
                fontSize = 36.sp,
                modifier = Modifier.clickable { navController.navigate("home") }
            )
            Text(
                text = "\uD83D\uDE03", // 😃 Mood Tracker (replace with suitable emoji)
                fontSize = 36.sp,
                modifier = Modifier.clickable { navController.navigate("mood") }
            )
            Text(
                text = "\u270D", // ✍ Journal
                fontSize = 36.sp,
                modifier = Modifier.clickable { navController.navigate("journal") }
            )
            Text(
                text = "\uD83D\uDCAC", // 💬 Community
                fontSize = 36.sp,
                modifier = Modifier.clickable { navController.navigate("community") }
            )
        }
    }
}

// 🌸 light pink button