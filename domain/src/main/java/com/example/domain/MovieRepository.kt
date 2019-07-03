package com.example.domain

import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional

interface MovieRepository {
    suspend fun getPopularMovies(): List<MovieEntity>
    suspend fun search(query: String): List<MovieEntity>
    suspend fun getMovie(movieId: Int): Optional<MovieEntity>
}