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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.breathebetter.viewmodel.CommunityViewModel

@Composable
fun CommunityScreen(navController: NavController, communityViewModel: CommunityViewModel) {
    val messages by communityViewModel.messages.collectAsState()
    var newMsg by remember { mutableStateOf("") }

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
                text = "\uD83D\uDD13", // 🔓 emoji
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Community Board (anonymous)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        // New message input
        OutlinedTextField(
            value = newMsg,
            onValueChange = { newMsg = it },
            label = { Text("Share something kind or helpful") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Post button
        Button(
            onClick = {
                if (newMsg.isNotBlank()) {
                    communityViewModel.postMessage(newMsg.trim())
                    newMsg = ""
                    communityViewModel.loadMessages()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Post anonymously")
        }

        Spacer(Modifier.height(16.dp))

        // Feed of messages
        Text("Feed", style = MaterialTheme.typography.titleMedium)
        messages.forEach {
            Text("${java.util.Date(it.timestamp)}")
            Text(it.content, modifier = Modifier.padding(bottom = 8.dp))
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

    LaunchedEffect(Unit) { communityViewModel.loadMessages() }
}