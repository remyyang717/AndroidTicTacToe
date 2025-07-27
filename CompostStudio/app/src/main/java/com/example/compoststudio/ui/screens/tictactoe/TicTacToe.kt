package com.example.compoststudio.ui.screens.tictactoe

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

                Spacer(modifier = Modifier.weight(1f))

                    Text("Round ${state.round}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.weight(0.5f))


                    GameBoard(state,viewModel)


                Spacer(modifier = Modifier.height(16.dp))

                    GameResultLabel(state)

                Spacer(modifier = Modifier.weight(1f))

                    GameControlButtons(viewModel,navController)
            }
        }
    }
}


@Composable
fun GameControlButtons(
    viewModel: TicTacToeViewModel,
    navController: NavController
){

    Row {
        Button(onClick = { viewModel.reDo() }) {
            Text("Redo")
        }
        Spacer(modifier = Modifier.width(36.dp))

        Button(onClick = { viewModel.unDo() }) {
            Text("Undo")
        }
    }
    Spacer(modifier = Modifier.height(48.dp))

    Row(modifier = Modifier.padding(bottom  = 24.dp)) {

        Button(
            onClick = { viewModel.resetGame() }) {
            Text("Restart")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(onClick = {
            viewModel.saveGame()
            navController.popBackStack()}) {
            Text("Save and Back")
        }
    }
}

@Composable
fun GameResultLabel(
    state: GameState
){
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
}

@Composable
fun GameBoard(
    state : GameState,
    viewModel: TicTacToeViewModel
){
    Box(
        modifier = Modifier
            .size(360.dp)
            .padding(16.dp)
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height


            drawLine(
                color = Color.Black,
                start = Offset(x = width / 3, y = 0f),
                end = Offset(x = width / 3, y = height),
                strokeWidth = 8f
            )
            drawLine(
                color = Color.Black,
                start = Offset(x = 2 * width / 3, y = 0f),
                end = Offset(x = 2 * width / 3, y = height),
                strokeWidth = 8f
            )


            drawLine(
                color = Color.Black,
                start = Offset(x = 0f, y = height / 3),
                end = Offset(x = width, y = height / 3),
                strokeWidth = 8f
            )
            drawLine(
                color = Color.Black,
                start = Offset(x = 0f, y = 2 * height / 3),
                end = Offset(x = width, y = 2 * height / 3),
                strokeWidth = 8f
            )
        }


        Column(modifier = Modifier.matchParentSize()) {
            for (i in 0..2) {
                Row(modifier = Modifier.weight(1f)) {
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { viewModel.makeMove(i, j) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.currentBoard.board[i][j],
                                fontSize = 58.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
