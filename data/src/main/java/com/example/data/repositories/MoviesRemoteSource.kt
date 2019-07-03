package com.example.data.repositories

import com.example.data.api.MovieApi
import com.example.data.mappers.MovieDataEntityMapper
import com.example.domain.entities.MovieEntity

class MoviesRemoteSource(
    private val api: MovieApi
) {

    private val movieDataMapper = MovieDataEntityMapper()

    suspend fun getPopularMovies(): List<MovieEntity> {
        return api.getPopularMovies().movies.map {
            movieDataMapper.mapFrom(it)
        }
    }
}