package com.example.compoststudio.di

import android.content.Context
import androidx.room.Room
import com.example.compoststudio.data.datasource.local.GameStateDatabase
import com.example.compoststudio.data.datasource.local.dao.GameStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideGameStateDatabase(@ApplicationContext context: Context): GameStateDatabase {
        return Room.databaseBuilder(
            context,
            GameStateDatabase::class.java,
            "gameState.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideGameStateDao(gameStateDatabase: GameStateDatabase): GameStateDao =
        gameStateDatabase.getGameStateDao()
}