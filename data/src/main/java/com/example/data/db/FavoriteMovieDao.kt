package com.example.data.db

import androidx.room.*
import com.example.data.entities.MovieData

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getFavorites(): List<MovieData>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    suspend fun get(movieId: Int): MovieData?

    @Query("SELECT * FROM movies WHERE title LIKE :query")
    suspend fun search(query: String): List<MovieData>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMovies(movies: List<MovieData>)

    @Query("DELETE FROM movies WHERE id=:movieId")
    suspend fun removeMovie(movieId: Int): Int

    @Query("DELETE FROM movies")
    suspend fun clear(): Int
}