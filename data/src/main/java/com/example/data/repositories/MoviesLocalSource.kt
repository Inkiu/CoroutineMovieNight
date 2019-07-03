package com.example.data.repositories

import com.example.domain.entities.MovieEntity

class MoviesLocalSource {

    private val movies = LinkedHashMap<Int, MovieEntity>() // 순서

    suspend fun getPopularMovies(): List<MovieEntity> {
        return movies.values.toList()
    }

    suspend fun putPopularMovies(movieEntities: List<MovieEntity>) {
        movieEntities.forEach { movies[it.id] = it }
    }
}