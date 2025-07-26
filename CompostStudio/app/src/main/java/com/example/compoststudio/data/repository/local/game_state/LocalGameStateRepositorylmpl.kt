package com.example.compoststudio.data.repository.local.game_state

import com.example.compoststudio.data.datasource.local.dao.GameStateDao
import com.example.compoststudio.data.model.GameState
import javax.inject.Inject

class LocalGameStateRepositoryImpl @Inject constructor(
    private val gameStateDao: GameStateDao
) : LocalGameStateRepository {
    override suspend fun insert(gameState: GameState) = gameStateDao.insert(gameState)
    override suspend fun getLatest(): GameState? = gameStateDao.getLatestGameState()
    override suspend fun getById(id: Int): GameState? = gameStateDao.getGameStateById(id)
    override suspend fun deleteById(id: Int) = gameStateDao.deleteGameStateById(id)
    override suspend fun getAll(): List<GameState> = gameStateDao.getAllGameState().filterNotNull()
}