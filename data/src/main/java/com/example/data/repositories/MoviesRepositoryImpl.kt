package com.example.data.repositories

import android.util.Log
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalSource,
    private val remoteDataSource: MoviesRemoteSource
) : MovieRepository {

    override suspend fun getPopularMovies(): List<MovieEntity> {
        var movies = localDataSource.getPopularMovies()
        if (movies.isEmpty()) {
            Log.d("tmpLog", "getPopularMovies: From Remote Source")
            movies = remoteDataSource.getPopularMovies()
            localDataSource.putPopularMovies(movies.map { it.copy() })
        }
        return movies
    }

    override suspend fun search(query: String): List<MovieEntity> {
        return remoteDataSource.getSearchedMovies(query)
    }

    override suspend fun getMovie(movieId: Int): MovieEntity {
        var movie = localDataSource.get(movieId)
        if (movie == null) {
            movie = remoteDataSource.getMovie(movieId)
            localDataSource.put(movie)
        }
        return movie
    }
}