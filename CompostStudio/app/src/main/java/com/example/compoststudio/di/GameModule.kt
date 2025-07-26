package com.example.compoststudio.di

import com.example.compoststudio.data.model.Board
import com.example.compoststudio.data.model.BoardHistory
import com.example.compoststudio.data.model.GameState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameModule {

    @Provides
    @Singleton
    fun provideInitialBoard(): Board {
        return Board()
    }

    @Provides
    @Singleton
    fun provideInitialBoardHistory(board: Board): BoardHistory {
        return BoardHistory(boardHistory = listOf(board))
    }


    @Provides
    @Singleton
    fun provideInitialGameState(board: Board): GameState {
        return GameState(
            currentBoard = board,
            currentPlayer = "X",
            winner = null,
            round = 0
        )
    }
}