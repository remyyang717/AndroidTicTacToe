package com.example.compoststudio.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.compoststudio.data.datasource.local.typeconverter.GameStateTypeConverter
import com.google.gson.annotations.SerializedName

data class Board(
    @SerializedName("board")
    val board: List<List<String>> = List(3) { List(3) { "" } },
)



@Entity(tableName = "Game_State")
@TypeConverters(GameStateTypeConverter::class)
data class GameState(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("currentBoard")
    val currentBoard: Board ,
    @SerializedName("currentPlayer")
    val currentPlayer: String,
    @SerializedName("winner")
    val winner: String?,
    @SerializedName("round")
    val round: Int,
    @SerializedName("boardHistory")
    val boardHistory: List<Board> = listOf(Board()),
)
{
    companion object {
    fun default() = GameState(
        currentBoard = Board(),
        currentPlayer = "X",
        winner = null,
        round = 0,
        boardHistory = listOf(Board())
    )
    }
}