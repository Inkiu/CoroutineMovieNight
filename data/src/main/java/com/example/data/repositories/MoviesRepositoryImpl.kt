package com.example.data.repositories

import android.util.Log
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional

class MoviesRepositoryImpl(
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMovie(movieId: Int): Optional<MovieEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}