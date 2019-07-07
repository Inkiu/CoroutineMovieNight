package com.example.data.repositories

import com.example.data.api.MovieApi
import com.example.data.mappers.DetailsDataMovieEntityMapper
import com.example.data.mappers.MovieDataEntityMapper
import com.example.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteSource @Inject constructor(
    private val api: MovieApi
) {

    private val movieDataMapper = MovieDataEntityMapper()
    private val detailDataMapper = DetailsDataMovieEntityMapper()

    suspend fun getPopularMovies(): List<MovieEntity> {
        return api.getPopularMovies().movies.map {
            movieDataMapper.mapFrom(it)
        }
    }

    suspend fun getMovie(movieId: Int): MovieEntity {
        return api.getMovieDetails(movieId).let {
            detailDataMapper.mapFrom(it)
        }
    }
}