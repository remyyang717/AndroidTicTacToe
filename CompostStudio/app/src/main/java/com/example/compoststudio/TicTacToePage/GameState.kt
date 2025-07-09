package com.example.compoststudio.TicTacToePage


data class Board(
    val value: List<List<String>> = List(3) { List(3) { "" } },
)

data class BoardHistory(
    val value: List<Board> = listOf(Board()),
)

data class GameState(
    val currentBoard: Board = Board(),
    val currentPlayer: String = "X",
    val winner: String? = null,
    val round: Int = 0,
)