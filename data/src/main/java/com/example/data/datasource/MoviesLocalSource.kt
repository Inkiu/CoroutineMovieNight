package com.example.data.datasource

import com.example.data.db.PopularMovieDatabase
import com.example.data.entities.MovieData
import com.example.data.mappers.MovieDataEntityMapper
import com.example.data.mappers.MovieEntityDataMapper
import com.example.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

class MoviesLocalSource (
    popularDatabase: PopularMovieDatabase,
    private val dataToEntityMapper: MovieDataEntityMapper,
    private val entityToDataMapper: MovieEntityDataMapper
) {

    private val popularDao = popularDatabase.getPopularMovieDao()

    private val movies = mutableMapOf<Int, MovieEntity>()

    suspend fun getPopularMovies(): List<MovieEntity> {
        return popularDao.getPopularMovies()
            .map { dataToEntityMapper.mapFrom(it) }
    }

    suspend fun putPopularMovies(movieEntities: List<MovieEntity>) {
        popularDao.saveMovies(
            movieEntities.map { entityToDataMapper.mapFrom(it) }
        )
    }

    suspend fun removeAllPopularMovies(): Int {
        return popularDao.clear()
    }

    suspend fun get(movieId: Int): MovieEntity? {
        return movies[movieId]
    }

    suspend fun put(movie: MovieEntity) {
        movies[movie.id] = movie.copy()
    }
}