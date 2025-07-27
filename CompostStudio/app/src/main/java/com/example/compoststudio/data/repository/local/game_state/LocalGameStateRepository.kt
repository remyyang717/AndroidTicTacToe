package com.example.compoststudio.data.repository.local.game_state

import com.example.compoststudio.data.model.GameState
import kotlinx.coroutines.flow.Flow

interface LocalGameStateRepository {

    fun getAll(): Flow<List<GameState>>
    
    suspend fun insert(gameState: GameState)
    suspend fun getById(id: Int): GameState?
    suspend fun deleteById(id: Int)

}