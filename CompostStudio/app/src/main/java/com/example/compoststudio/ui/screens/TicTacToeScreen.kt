package com.example.compoststudio.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compoststudio.viewmodel.TicTacToeViewModel

@Composable
fun TicTacToeScreen(viewModel: TicTacToeViewModel = viewModel()) {
    val state = viewModel.gameState

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(state.currentBoard.value[i][j], fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.winner != null -> Text("Winner: ${state.winner}", fontSize = 24.sp, color = Color.Green)
            state.currentBoard.value.all { row -> row.all { it != "" } } -> Text("It's a draw!", fontSize = 24.sp, color = Color.Gray)
            else -> Text("Turn: ${state.currentPlayer}", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.resetGame() }) {
            Text("Restart")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(onClick = { viewModel.unDo() }) {
                Text("Undo")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.reDo()  }) {
                Text("Redo")
            }
        }
    }
}