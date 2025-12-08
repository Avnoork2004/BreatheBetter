package com.example.breathebetter

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.breathebetter.viewmodel.AuthViewModel
import com.example.breathebetter.viewmodel.AuthViewModelFactory
import com.example.breathebetter.viewmodel.CommunityViewModel
import com.example.breathebetter.viewmodel.JournalViewModel
import com.example.breathebetter.viewmodel.MoodViewModel

// defines the nav graph for the app.
// maps different strings to their screens which are all the files
@Composable
fun AppNavHost(navController: NavHostController) {

    val application = LocalContext.current.applicationContext as Application
    val authFactory = AuthViewModelFactory(application)
    val authViewModel: AuthViewModel = viewModel(factory = authFactory)
    val moodViewModel: MoodViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))
    val journalViewModel: JournalViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))
    val communityViewModel: CommunityViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))

    //splash screen shows when the app starts
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("auth") { AuthChoiceScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegistrationScreen(navController, authViewModel) }
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("mood") { MoodTrackerScreen(navController, authViewModel, moodViewModel) }
        composable("journal") { JournalScreen(navController, authViewModel, journalViewModel) }
        composable("community") { CommunityScreen(navController, communityViewModel) }
    }
}