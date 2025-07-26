package com.example.compoststudio.ui.screens.tictactoe


import androidx.lifecycle.ViewModel
import com.example.compoststudio.data.model.Board
import com.example.compoststudio.data.model.BoardHistory
import com.example.compoststudio.data.model.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.collections.get
import kotlin.collections.plus
import kotlin.text.get


@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val initialState: GameState,
    private val initialHistory: BoardHistory
) : ViewModel() {

    private val _gameState = MutableStateFlow(initialState)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _history = MutableStateFlow(initialHistory)
    val history: StateFlow<BoardHistory> = _history.asStateFlow()

    fun makeMove(row: Int, col: Int) {
        val currentState = _gameState.value
        val currentBoard = currentState.currentBoard.board

        if (currentBoard[row][col] == "" && currentState.winner == null) {
            val updatedBoard = currentBoard.mapIndexed { r, rowList ->
                rowList.mapIndexed { c, cell ->
                    if (r == row && c == col) currentState.currentPlayer else cell
                }
            }

            val winner = checkWinner(updatedBoard)
            val nextPlayer = if (currentState.currentPlayer == "X") "O" else "X"
            val trimmedBoardHistory = if (currentState.round < _history.value.boardHistory.size) {
                _history.value.boardHistory.slice(0..currentState.round)
            } else {
                _history.value.boardHistory
            }

            _gameState.value = currentState.copy(
                currentBoard = Board(updatedBoard),
                winner = winner,
                currentPlayer = if (winner == null) nextPlayer else currentState.currentPlayer,
                round = currentState.round + 1
            )

            _history.value = _history.value.copy(
                boardHistory = trimmedBoardHistory + _gameState.value.currentBoard
            )
        }
    }

    fun unDo() {
        val round = _gameState.value.round - 1
        if (round < 0) return

        val board = _history.value.boardHistory[round]

        _gameState.value = _gameState.value.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = null
        )
    }

    fun reDo() {
        val round = _gameState.value.round + 1
        if (round > _history.value.boardHistory.lastIndex) return

        val board = _history.value.boardHistory[round]
        val winner = checkWinner(board.board)

        _gameState.value = _gameState.value.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = winner
        )
    }

    fun resetGame() {
        _gameState.value = initialState.copy()
        _history.value = initialHistory.copy()
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