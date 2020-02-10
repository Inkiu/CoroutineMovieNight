package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entities.MovieData

@Database(entities = [MovieData::class], version = 1)
abstract class PopularMovieDatabase: RoomDatabase() {
    abstract fun getPopularMovieDao(): PopularMovieDao
}