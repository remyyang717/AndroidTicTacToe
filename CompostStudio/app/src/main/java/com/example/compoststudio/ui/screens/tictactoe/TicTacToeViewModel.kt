package com.example.compoststudio.ui.screens.tictactoe


import androidx.lifecycle.ViewModel
import com.example.compoststudio.data.model.Board
import com.example.compoststudio.data.model.GameState
import com.example.compoststudio.data.repository.local.game_state.LocalGameStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.collections.plus
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val localGameStateRepository: LocalGameStateRepository,
) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState.default())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _gameIdList = MutableStateFlow<List<Int>>(emptyList())
    val gameIdList: StateFlow<List<Int>> = _gameIdList

    init {
        loadGameIds()
    }


    fun loadGame(shouldReset: Boolean, existingState: GameState? = null) {
        viewModelScope.launch {
            _gameState.value = if (shouldReset || existingState == null) {
                GameState.default()
            } else {
                existingState
            }
        }
    }



    suspend fun getSavedGameById(id:Int): GameState? {
        return localGameStateRepository.getById(id)
    }

    suspend fun getAllGames(): List<GameState>? {
        return localGameStateRepository.getAll()
    }

    fun loadGameIds() {
        viewModelScope.launch {
            val games = getAllGames()
            _gameIdList.value = games?.map { it.id } ?: emptyList()
        }
    }

    fun deleteGame(id: Int) {
        viewModelScope.launch {
            localGameStateRepository.deleteById(id)
            loadGameIds()
        }
    }



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
            val trimmedBoardHistory = if (currentState.round < _gameState.value.boardHistory.size) {
                _gameState.value.boardHistory.slice(0..currentState.round)
            } else {
                _gameState.value.boardHistory
            }

            _gameState.value = currentState.copy(
                currentBoard = Board(updatedBoard),
                winner = winner,
                currentPlayer = if (winner == null) nextPlayer else currentState.currentPlayer,
                round = currentState.round + 1
            )

            _gameState.value = _gameState.value.copy(
                boardHistory = trimmedBoardHistory + _gameState.value.currentBoard
            )
        }
    }

    fun unDo() {
        val round = _gameState.value.round - 1
        if (round < 0) return

        val board = _gameState.value.boardHistory[round]

        _gameState.value = _gameState.value.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = null
        )
    }

    fun reDo() {
        val round = _gameState.value.round + 1
        if (round > _gameState.value.boardHistory.lastIndex) return

        val board = _gameState.value.boardHistory[round]
        val winner = checkWinner(board.board)

        _gameState.value = _gameState.value.copy(
            currentBoard = board,
            currentPlayer = if (round % 2 == 0) "X" else "O",
            round = round,
            winner = winner
        )
    }

    fun resetGame() {
        _gameState.value = GameState.default()
    }

    fun saveGame() {
        viewModelScope.launch {
            localGameStateRepository.insert(_gameState.value)
        }
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