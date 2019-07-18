package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entities.MovieData

@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class FavoriteMovieDatabase: RoomDatabase() {
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}