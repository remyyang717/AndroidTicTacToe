package com.example.compoststudio.model

data class GameState(
    val board: List<List<String>> = List(3) { List(3) { "" } },
    val currentPlayer: String = "X",
    val winner: String? = null
)