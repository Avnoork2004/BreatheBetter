package com.example.breathebetter

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// defines the nav graph for the app.
// maps different strings to their screens which are all the files
@Composable
fun AppNavHost(navController: NavHostController) {
    //splash screen shows when the app starts
    NavHost(navController = navController, startDestination = "splash") {
        //splash screen
        composable("splash") { SplashScreen(navController) }
        //"welcome" choice screen with login and register btns
        composable("auth") { AuthChoiceScreen(navController) }
        //registration screen
        composable("register") { RegistrationScreen(navController) }
        //login screen
        composable("login") { LoginScreen(navController) }
        //homescreen
        composable("home") { HomeScreen(navController) }
    }
}