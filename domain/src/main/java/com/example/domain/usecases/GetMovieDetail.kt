package com.example.domain.usecases

import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import com.example.domain.entities.Optional

class GetMovieDetail(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieDetail.Param, MovieEntity> {

    override suspend fun invoke(param: Param): MovieEntity {
        return movieRepository.getMovie(param.movieId)
    }

    data class Param(
        val movieId: Int
    )
}