package com.example.compoststudio.data.repository.local.game_state

import com.example.compoststudio.data.model.GameState

interface LocalGameStateRepository {
    suspend fun insert(gameState: GameState)
    suspend fun getLatest(): GameState?
    suspend fun getById(id: Int): GameState?
    suspend fun deleteById(id: Int)
    suspend fun getAll(): List<GameState>
}