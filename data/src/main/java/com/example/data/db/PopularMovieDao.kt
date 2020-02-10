package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.MovieData

@Dao
interface PopularMovieDao {

    @Query("SELECT * from movies")
    suspend fun getPopularMovies(): List<MovieData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieData>)

    @Query("DELETE FROM movies")
    suspend fun clear(): Int
}