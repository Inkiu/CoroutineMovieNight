package com.example.domain

import com.example.domain.entities.MovieEntity

interface FavoriteMovieRepository {
    // Create
    suspend fun save(movieEntity: MovieEntity)
    suspend fun saveAll(moviesEntities: List<MovieEntity>)

    // Update

    // Read
    suspend fun get(movieId: Int): MovieEntity?
    suspend fun getAll(): List<MovieEntity>
    suspend fun search(query: String): List<MovieEntity>
    suspend fun isEmpty(): Boolean

    // Delete
    suspend fun remove(movieId: Int)
    suspend fun clear()
}