package com.example.data.repositories

import android.util.Log
import com.example.data.datasource.MoviesLocalSource
import com.example.data.datasource.MoviesRemoteSource
import com.example.data.mappers.DetailsDataMovieEntityMapper
import com.example.data.mappers.MovieDataEntityMapper
import com.example.data.mappers.MovieEntityDataMapper
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalSource,
    private val remoteDataSource: MoviesRemoteSource,
    private val dataToEntityMapper: MovieDataEntityMapper,
    private val entityToDataMapper: MovieEntityDataMapper,
    private val detailMovieMapper: DetailsDataMovieEntityMapper
) : MovieRepository {

    override fun getPopularMoviesAsFlow(): Flow<List<MovieEntity>> {
        return flow {
                localDataSource.getPopularMovies()
                    .map(dataToEntityMapper::mapFrom)
                    .let {
                        if (it.isNotEmpty()) emit(it)
                    }
                remoteDataSource.getPopularMovies()
                    .also {
                        localDataSource.removeAllPopularMovies()
                        localDataSource.putPopularMovies(it)
                    }
                    .map(dataToEntityMapper::mapFrom)
                    .let { emit(it) }
            }
    }

    override suspend fun getPopularMovies(): List<MovieEntity> { // TODO - flow
        var movies = localDataSource.getPopularMovies()
        if (movies.isEmpty()) {
            Log.d("tmpLog", "getPopularMovies: From Remote Source")
            movies = remoteDataSource.getPopularMovies()
            localDataSource.putPopularMovies(movies.map { it.copy() })
        }
        return movies.map(dataToEntityMapper::mapFrom)
    }

    override suspend fun search(query: String): List<MovieEntity> {
        return remoteDataSource.getSearchedMovies(query)
            .map(dataToEntityMapper::mapFrom)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieEntity {
        val detail = localDataSource.getDetail(movieId) ?: remoteDataSource.getDetail(movieId).also {
            localDataSource.putDetail(it)
        }
        return detail.let(detailMovieMapper::mapFrom)
    }
}