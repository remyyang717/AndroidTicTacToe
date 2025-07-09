package com.example.compoststudio.TicTacToePage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.collections.plus

class TicTacToeViewModel : ViewModel() {

    var gameState by mutableStateOf(GameState())
        private set

    var boardHistory by mutableStateOf(BoardHistory())
        private set

    fun makeMove(row: Int, col: Int) {
        if (gameState.currentBoard.value[row][col] == "" && gameState.winner == null) {
            val updatedBoard = gameState.currentBoard.value.mapIndexed { r, rowList ->
                rowList.mapIndexed { c, cell ->
                    if (r == row && c == col) gameState.currentPlayer else cell
                }
            }

            val winner = checkWinner(updatedBoard)
            val nextPlayer = if (gameState.currentPlayer == "X") "O" else "X"

            val trimmedBoardHistory = if (gameState.round < boardHistory.value.count()) {
                boardHistory.value.slice(0..gameState.round)
            } else {
                boardHistory.value
            }

            gameState = gameState.copy(
                currentBoard = Board(updatedBoard),
                winner = winner,
                currentPlayer = if (winner == null) nextPlayer else gameState.currentPlayer,
                round = gameState.round + 1
            )

            boardHistory = boardHistory.copy(
                value = trimmedBoardHistory + gameState.currentBoard
            )
        }
    }

    fun unDo() {
        val round = gameState.round - 1

        if (round < 0) return

        val board = boardHistory.value[round]

        gameState = gameState.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = null,
        )
    }

    fun reDo(){
        val round = gameState.round + 1
        if (round > boardHistory.value.count() -1) return

        val board = boardHistory.value[round]

        val winner = checkWinner(boardHistory.value[round].value)

        gameState = gameState.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = winner,
        )
    }


    fun resetGame() {
        gameState = GameState()
        boardHistory = BoardHistory()
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