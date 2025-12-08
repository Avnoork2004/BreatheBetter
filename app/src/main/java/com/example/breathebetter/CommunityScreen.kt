package com.example.breathebetter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
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

    Box(modifier = Modifier.fillMaxSize()) {

        // -------------------
        // Scrollable content
        // -------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 100.dp) // leave space for top & bottom bars
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Text("Community Board (anonymous)", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newMsg,
                onValueChange = { newMsg = it },
                label = { Text("Share something kind or helpful") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF967BB6),
                    contentColor = Color.White
                ),
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

            Spacer(modifier = Modifier.height(16.dp))

            Text("Feed", style = MaterialTheme.typography.titleMedium)
            messages.forEach {
                Text("${java.util.Date(it.timestamp)}")
                Text(it.content, modifier = Modifier.padding(bottom = 8.dp))
            }
        }

        // -------------------
        // Fixed Top Bar
        // -------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp).padding(end = 8.dp)
                )
                Text(
                    text = "Welcome to BreatheBetter",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "\uD83D\uDD13", // logout emoji
                fontSize = 30.sp,
                modifier = Modifier.clickable {
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        // -------------------
        // Fixed Bottom Navigation Bar
        // -------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("\uD83C\uDFE0", fontSize = 36.sp, modifier = Modifier.clickable { navController.navigate("home") })
            Text("\uD83D\uDE03", fontSize = 36.sp, modifier = Modifier.clickable { navController.navigate("mood") })
            Text("✍", fontSize = 36.sp, modifier = Modifier.clickable { navController.navigate("journal") })
            Text("\uD83D\uDCAC", fontSize = 36.sp, modifier = Modifier.clickable { navController.navigate("community") })
        }
    }


    LaunchedEffect(Unit) { communityViewModel.loadMessages() }
}