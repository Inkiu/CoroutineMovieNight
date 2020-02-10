package com.example.domain.usecases

import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity

class GetMovieDetail(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieDetail.Param, MovieEntity> {

    override suspend fun invoke(param: Param): MovieEntity {
        return movieRepository.getMovieDetail(param.movieId)
    }

    data class Param(
        val movieId: Int
    )
}