package com.example.breathebetter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.breathebetter.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

// --- DATE VALIDATION ---
fun isValidDate(dob: String): Boolean {
    if (dob.length != 10) return false
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    sdf.isLenient = false
    return try {
        sdf.parse(dob)
        true
    } catch (e: Exception) {
        false
    }
}

@Composable
fun RegistrationScreen(navController: NavController, authViewModel: AuthViewModel) {

    // Form fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var localError by remember { mutableStateOf("") }
    val authError by authViewModel.authError.collectAsState(initial = null)

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // center the Column
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
                .fillMaxWidth(0.9f),
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


            Text("Register", style = MaterialTheme.typography.headlineMedium)

            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    if (filtered.length <= 30) firstName = filtered
                },
                label = { Text("First Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    if (filtered.length <= 30) lastName = filtered
                },
                label = { Text("Last Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // DOB
            OutlinedTextField(
                value = dob,
                onValueChange = { newValue ->
                    val digits = newValue.text.filter { it.isDigit() }.take(8)
                    val formatted = buildString {
                        digits.forEachIndexed { index, c ->
                            append(c)
                            if ((index == 1 || index == 3) && index != digits.lastIndex) append("/")
                        }
                    }
                    dob = TextFieldValue(formatted, selection = TextRange(formatted.length))
                },
                label = { Text("Date of Birth (MM/DD/YYYY)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Error messages
            if (localError.isNotEmpty()) {
                Text(localError, color = MaterialTheme.colorScheme.error)
            }
            if (!authError.isNullOrEmpty()) {
                Text(authError!!, color = MaterialTheme.colorScheme.error)
            }

            // Buttons Row: Sign Up + Home
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        // Local validation
                        localError = when {
                            firstName.length !in 3..30 -> "First name must be 3–30 characters"
                            lastName.length !in 1..30 -> "Last name must be 1–30 characters"
                            dob.text.isEmpty() -> "Date of birth cannot be empty"
                            !isValidDate(dob.text) -> "Invalid date of birth (MM/DD/YYYY)"
                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email address"
                            password.length < 6 -> "Password must be at least 6 characters"
                            else -> ""
                        }

                        if (localError.isEmpty()) {
                            authViewModel.register(
                                first = firstName.trim(),
                                last = lastName.trim(),
                                email = email.trim(),
                                password = password
                            ) {
                                navController.navigate("auth") {
                                    popUpTo("register") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sign Up")
                }

                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Log-in")
                }
            }
        }
    }
}