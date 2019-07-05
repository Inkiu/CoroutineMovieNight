package com.example.domain

import com.example.domain.entities.MovieEntity

interface FavoriteMovieRepository {
    suspend fun save(movieEntity: MovieEntity)
    suspend fun remove(movieId: Int)
}