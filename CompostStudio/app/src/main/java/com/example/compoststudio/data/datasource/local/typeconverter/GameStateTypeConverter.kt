package com.example.compoststudio.data.datasource.local.typeconverter


import androidx.room.TypeConverter
import com.example.compoststudio.data.model.Board
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GameStateTypeConverter {

    private val gson : Gson by lazy {
        Gson()
    }

    // Converter for a single Board object (used in currentBoard)
    @TypeConverter
    fun fromBoard(value: Board): String = gson.toJson(value)

    @TypeConverter
    fun toBoard(value: String): Board =
        gson.fromJson(value, object : TypeToken<Board>() {}.type)

    // Converter for List<Board> (used in boardHistory)
    @TypeConverter
    fun fromBoardList(value: List<Board>): String = gson.toJson(value)

    @TypeConverter
    fun toBoardList(value: String): List<Board> =
        gson.fromJson(value, object : TypeToken<List<Board>>() {}.type)

}