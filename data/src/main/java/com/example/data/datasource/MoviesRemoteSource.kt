package com.example.data.datasource

import com.example.data.api.MovieApi
import com.example.data.entities.DetailsData
import com.example.data.entities.MovieData
import com.example.data.mappers.DetailsDataMovieEntityMapper
import com.example.data.mappers.MovieDataEntityMapper
import com.example.domain.entities.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteSource @Inject constructor(
    private val api: MovieApi
) {

    suspend fun getPopularMovies(): List<MovieData> {
        return api.getPopularMovies().movies
    }

    suspend fun getSearchedMovies(str: String): List<MovieData> {
        return api.searchMovies(str).movies
    }

    suspend fun getDetail(movieId: Int): DetailsData {
        return api.getMovieDetails(movieId)
    }
}