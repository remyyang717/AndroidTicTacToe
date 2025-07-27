package com.example.compoststudio.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compoststudio.data.model.GameState
import kotlinx.coroutines.flow.Flow

@Dao
interface GameStateDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameState: GameState)

    @Query("SELECT * FROM Game_State ORDER BY id DESC LIMIT 1")
    suspend fun getLatestGameState(): GameState?

    @Query("Select * From Game_State Where id = :id")
    suspend fun getGameStateById(id: Int): GameState?

    @Query("DELETE FROM Game_State WHERE id = :id")
    suspend fun deleteGameStateById(id: Int)

    @Query("SELECT * FROM Game_State")
    fun getAllGameState(): Flow<List<GameState>>
}