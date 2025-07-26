package com.example.compoststudio.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.compoststudio.data.model.GameState
import com.example.compoststudio.data.datasource.local.dao.GameStateDao
import com.example.compoststudio.data.datasource.local.typeconverter.GameStateTypeConverter


@Database(version = 1, entities = [GameState::class], exportSchema = false)
@TypeConverters(GameStateTypeConverter::class)
abstract class GameStateDatabase : RoomDatabase() {
    abstract fun getGameStateDao(): GameStateDao
}
