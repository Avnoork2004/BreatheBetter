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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun MoodTrackerScreen(navController: NavController, authViewModel: AuthViewModel, moodViewModel: MoodViewModel) {
    val userId by authViewModel.currentUserId.collectAsState(initial = null)
    val moods by moodViewModel.moods.collectAsState()

    var note by remember { mutableStateOf("") }

    // 6 emoji moods (2 rows × 3)
    val moodOptions = listOf("😊", "😢", "😟", "😡", "😫", "🙂", "\uD83D\uDE2D", "\uD83D\uDE0C", "\uD83D\uDE13")
    val chunkedMoods = moodOptions.chunked(3)

    Box(modifier = Modifier.fillMaxSize()) {

        // -------------------
        // Scrollable content
        // -------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo + Welcome
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
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
                    text = "\uD83D\uDD12",
                    fontSize = 30.sp,
                    modifier = Modifier.clickable {
                        authViewModel.logout()
                        navController.navigate("auth") { popUpTo("home") { inclusive = true } }
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text("How are you feeling today?", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))

            // Emoji grid
            chunkedMoods.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { mood ->
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(Color(0xFF967BB6), RoundedCornerShape(12.dp))
                                .clickable {
                                    userId?.let { uid ->
                                        moodViewModel.addMood(uid, mood, note.ifBlank { null })
                                        note = ""
                                        moodViewModel.loadMoods(uid)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = mood, fontSize = 40.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Note input with submit
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Optional note about your mood") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF967BB6), RoundedCornerShape(10.dp))
                        .padding(vertical = 12.dp, horizontal = 20.dp)
                        .clickable {
                            userId?.let { uid ->
                                moodViewModel.addMood(uid, "📝", note.ifBlank { null })
                                note = ""
                                moodViewModel.loadMoods(uid)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Submit", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent moods
            Text("Recent moods", style = MaterialTheme.typography.titleMedium)
            moods.take(5).forEach {
                Text(
                    "${it.mood} — ${java.util.Date(it.timestamp)} ${it.note?.let { note -> "- $note" } ?: ""}",
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp)) // Extra padding so last item doesn't overlap nav bar
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


    // Load moods whenever the userId changes
    LaunchedEffect(userId) {
        userId?.let { moodViewModel.loadMoods(it) }
    }
}