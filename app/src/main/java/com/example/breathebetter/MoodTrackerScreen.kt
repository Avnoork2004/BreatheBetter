package com.example.breathebetter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.breathebetter.viewmodel.AuthViewModel
import com.example.breathebetter.viewmodel.MoodViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun MoodTrackerScreen(navController: NavController, authViewModel: AuthViewModel, moodViewModel: MoodViewModel) {
    val userId by authViewModel.currentUserId.collectAsState(initial = null)
    val moods by moodViewModel.moods.collectAsState()

    var note by remember { mutableStateOf("") }
    val moodOptions = listOf("Happy", "Okay", "Sad", "Anxious", "Angry", "Stressed")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
        // Mood tracker header
        Text("How are you feeling today?", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        // Mood buttons
        moodOptions.forEach { mood ->
            Button(
                onClick = {
                    userId?.let { uid ->
                        moodViewModel.addMood(uid, mood, note.ifBlank { null })
                        note = ""
                        moodViewModel.loadMoods(uid)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(mood)
            }
        }

        // Optional note input
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Optional note") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Recent moods
        Text("Recent moods", style = MaterialTheme.typography.titleMedium)
        moods.take(5).forEach {
            Text(
                "${it.mood} — ${java.util.Date(it.timestamp)} ${it.note?.let { note -> "- $note" } ?: ""}",
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

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


    // Load moods whenever the userId changes
    LaunchedEffect(userId) {
        userId?.let { moodViewModel.loadMoods(it) }
    }
}