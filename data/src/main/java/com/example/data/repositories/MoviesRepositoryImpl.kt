package com.example.data.repositories

import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteSource
) : MovieRepository {
    override suspend fun getPopularMovies(): List<MovieEntity> {
        return remoteDataSource.getPopularMovies()
    }

    override suspend fun search(query: String): List<MovieEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMovie(movieId: Int): Optional<MovieEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}