package com.example.compoststudio.ui.screens.tictactoe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compoststudio.data.model.GameState

@Composable
fun TicTacToe(
    navController: NavController,
    shouldReset: Boolean = false,
    initialGameState: GameState = GameState.default(), // fallback
    viewModel: TicTacToeViewModel = hiltViewModel()
) {

    LaunchedEffect(shouldReset, initialGameState) {
        viewModel.loadGame(shouldReset, initialGameState)
    }

    val state by viewModel.gameState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text("Tic Tac Toe", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Round ${state.round}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))

                for (i in 0..2) {
                    Row {
                        for (j in 0..2) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(4.dp)
                                    .clickable { viewModel.makeMove(i, j) },
                            ) {
                                Surface(
                                    border = BorderStroke(1.dp, Color.Black),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            state.currentBoard.board[i][j],
                                            fontSize = 36.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    state.winner != null -> Text(
                        "Winner: ${state.winner}",
                        fontSize = 24.sp,
                        color = Color.Green
                    )

                    state.currentBoard.board.all { row -> row.all { it != "" } } -> Text(
                        "It's a draw!",
                        fontSize = 24.sp,
                        color = Color.Gray
                    )

                    else -> Text("Turn: ${state.currentPlayer}", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.resetGame() }) {
                    Text("Restart")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = { viewModel.unDo() }) {
                        Text("Undo")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { viewModel.reDo() }) {
                        Text("Redo")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.padding(bottom  = 24.dp)) {
                    Button(onClick = {
                        viewModel.saveGame()
                        navController.popBackStack()}) {
                        Text("Save and Back")
                    }
                }
            }
        }
    }
}


