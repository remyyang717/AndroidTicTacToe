package com.example.compoststudio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import com.example.compoststudio.model.GameState

class TicTacToeViewModel : ViewModel() {

    var gameState by mutableStateOf(GameState())
        private set

    fun makeMove(row: Int, col: Int) {
        if (gameState.board[row][col] == "" && gameState.winner == null) {
            val updatedBoard = gameState.board.mapIndexed { r, rowList ->
                rowList.mapIndexed { c, cell ->
                    if (r == row && c == col) gameState.currentPlayer else cell
                }
            }

            val winner = checkWinner(updatedBoard)
            val nextPlayer = if (gameState.currentPlayer == "X") "O" else "X"

            gameState = gameState.copy(
                board = updatedBoard,
                winner = winner,
                currentPlayer = if (winner == null) nextPlayer else gameState.currentPlayer
            )
        }
    }

    fun resetGame() {
        gameState = GameState()
    }

    private fun checkWinner(board: List<List<String>>): String? {
        for (i in 0..2) {
            if (board[i][0] != "" && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0]
            if (board[0][i] != "" && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i]
        }
        if (board[0][0] != "" && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0]
        if (board[0][2] != "" && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2]
        return null
    }
}