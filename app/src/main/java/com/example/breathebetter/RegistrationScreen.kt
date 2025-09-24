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
import java.text.SimpleDateFormat
import java.util.Locale

// Checks if DOB is valid in MM/DD/YYYY
fun isValidDate(dob: String): Boolean {
    if (dob.length != 10) return false
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    sdf.isLenient = false
    return try {
        sdf.parse(dob) // exception if invalid
        true
    } catch (e: Exception) {
        false
    }
}

@Composable
fun RegistrationScreen(navController: NavController) {
    //form feilds
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // main layout
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)  // scrolling
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Register", style = MaterialTheme.typography.headlineMedium)

            // First Name
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    if (filtered.length <= 30) firstName = filtered
                },
                label = { Text("First Name") }
            )

            // Last Name
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    if (filtered.length <= 30) lastName = filtered
                },
                label = { Text("Last Name") }
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            //shows errors
            if (errorMsg.isNotEmpty()) Text(errorMsg, color = MaterialTheme.colorScheme.error)

            //signup btn
            Button(onClick = {
                //validates the feilds
                errorMsg = when {
                    firstName.length < 3 || firstName.length > 30 -> "First name must be 3-30 characters"
                    lastName.length < 1 || lastName.length > 30 -> "Last name must be 1-30 characters"
                    dob.text.isEmpty() -> "Date of birth cannot be empty"
                    !isValidDate(dob.text) -> "Invalid date of birth"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email"
                    password.length < 6 -> "Password must be at least 6 characters"
                    else -> ""
                }
                //if all is good go bck to the auth login/register btn screen
                if (errorMsg.isEmpty()) navController.navigate("auth")
            }) {
                Text("Sign Up")
            }
        }
    }
}