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
import com.example.breathebetter.viewmodel.AuthViewModel
import com.example.breathebetter.viewmodel.JournalViewModel

@Composable
fun JournalScreen(navController: NavController, authViewModel: AuthViewModel, journalViewModel: JournalViewModel) {
    val userId by authViewModel.currentUserId.collectAsState(initial = null)
    val journals by journalViewModel.journals.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

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

        // Journal header
        Text("Journal", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        // Title input (optional)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Content input
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Write your reflection") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(8.dp))

        // Save button
        Button(
            onClick = {
                userId?.let {
                    journalViewModel.addJournal(it, title.ifBlank { null }, content)
                    title = ""
                    content = ""
                    journalViewModel.loadJournals(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        Spacer(Modifier.height(16.dp))

        // Recent entries
        Text("Recent entries", style = MaterialTheme.typography.titleMedium)
        journals.forEach {
            Text("${java.util.Date(it.timestamp)} — ${it.title ?: "No title"}")
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


    // Load journals when userId changes
    LaunchedEffect(userId) {
        userId?.let { journalViewModel.loadJournals(it) }
    }
}