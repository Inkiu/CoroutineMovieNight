package com.example.domain.usecases

import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity

class GetPopularMovies(
    private val movieRepository: MovieRepository
) : UseCase<Unit, List<MovieEntity>> {

    override suspend fun invoke(param: Unit): List<MovieEntity> {
        return movieRepository.getPopularMovies()
    }

}