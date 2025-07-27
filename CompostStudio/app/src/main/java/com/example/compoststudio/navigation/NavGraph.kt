package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compoststudio.data.model.GameState
import com.example.compoststudio.ui.screens.mainscreen.MainScreen
import com.example.compoststudio.ui.screens.tictactoe.TicTacToe
import com.example.compoststudio.ui.screens.tictactoe.TicTacToeViewModel


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen( navController = navController,)
        }

        composable("new_game") {
            TicTacToe(  navController = navController,
                        shouldReset = true
            ) // or pass viewModel if needed
        }

        composable("continue_game/{id}") { backStackEntry ->
            val viewModel: TicTacToeViewModel = hiltViewModel()
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()

            val existingStateState = produceState<GameState?>(initialValue = null) {
                value = id?.let { viewModel.getSavedGameById(it) }
            }

            val existingState = existingStateState.value

            existingState?.let {
                TicTacToe(
                    navController = navController,
                    shouldReset = false,
                    initialGameState = existingState
                )
            } ?: run {
                androidx.compose.material3.Text("No saved game found.")
            }
        }
    }
}
