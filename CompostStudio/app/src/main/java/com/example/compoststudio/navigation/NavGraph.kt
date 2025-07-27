package com.example.compoststudio.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compoststudio.ui.screens.mainscreen.MainScreen
import com.example.compoststudio.ui.screens.tictactoe.TicTacToe


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen( navController = navController)
        }

        composable("new_game") {
            TicTacToe(  navController = navController,
                        shouldReset = true
            )
        }

        composable("continue_game/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()

            id?.let {
                TicTacToe(
                    navController = navController,
                    shouldReset = false,
                    savedGameId = it
                )
            }  ?: run {
                Text("No saved game found.")
            }
        }
    }
}
