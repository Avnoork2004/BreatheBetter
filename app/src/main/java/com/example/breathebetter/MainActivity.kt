package com.example.breathebetter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.breathebetter.ui.theme.BreatheBetterTheme



import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController


//entry point of the app
//uses jetpack compose and initializes nav
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                //background for ui
                Surface(color = MaterialTheme.colorScheme.background) {
                    //handles navigation between screens
                    val navController = rememberNavController()
                    //calls navgraph & controls which screen is shown
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}