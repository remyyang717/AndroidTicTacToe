package com.example.compoststudio.data.model
import com.google.gson.annotations.SerializedName

data class Board(
    @SerializedName("board")
    val board: List<List<String>> = List(3) { List(3) { "" } },
)

data class BoardHistory(
    @SerializedName("boardHistory")
    val boardHistory: List<Board> = listOf(Board()),
)

data class GameState(
    @SerializedName("currentBoard")
    val currentBoard: Board ,
    @SerializedName("currentPlayer")
    val currentPlayer: String,
    @SerializedName("winner")
    val winner: String?,
    @SerializedName("round")
    val round: Int,
)