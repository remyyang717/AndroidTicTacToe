package com.example.compoststudio.data.repository.local.game_state

import com.example.compoststudio.data.model.GameState
import kotlinx.coroutines.flow.Flow

interface LocalGameStateRepository {
    suspend fun insert(gameState: GameState)
    suspend fun getLatest(): GameState?
    suspend fun getById(id: Int): GameState?
    suspend fun deleteById(id: Int)
    fun getAll(): Flow<List<GameState>>
}