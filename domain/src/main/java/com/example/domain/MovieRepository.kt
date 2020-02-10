package com.example.domain

import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<List<MovieEntity>>
    suspend fun search(query: String): List<MovieEntity>
    suspend fun getMovieDetail(movieId: Int): MovieEntity
}