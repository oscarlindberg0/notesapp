package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun App() {
    //I learned how to switch/manage screens from here: https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "StartScreen") {
        composable("StartScreen"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val text = backStackEntry.arguments?.getString("text") ?: ""
            StartScreen(navController = navController, name = name, text = text)
        }
        composable(
            route = "EditNoteScreen?name={name}&text={text}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("text") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val text = backStackEntry.arguments?.getString("text") ?: ""
            EditNoteScreen(navController = navController, name = name, text = text)
        }
    }
}