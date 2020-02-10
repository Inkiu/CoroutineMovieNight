package com.example.data.datasource

import com.example.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

class MoviesLocalSource {

    private val popularMovies = LinkedHashMap<Int, MovieEntity>() // 순서
    private val movies = mutableMapOf<Int, MovieEntity>()

    suspend fun getPopularMovies(): List<MovieEntity> {
        return popularMovies.values.toList()
    }

    suspend fun putPopularMovies(movieEntities: List<MovieEntity>) {
        movieEntities.forEach { popularMovies[it.id] = it }
    }

    suspend fun get(movieId: Int): MovieEntity? {
        return movies[movieId]
    }

    suspend fun put(movie: MovieEntity) {
        movies[movie.id] = movie.copy()
    }
}