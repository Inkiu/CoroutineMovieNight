package com.example.domain.usecases

import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity

class SearchMovies(
    private val movieRepository: MovieRepository
) : UseCase<SearchMovies.Param, List<MovieEntity>> {

    override suspend fun invoke(param: Param): List<MovieEntity> {
        return movieRepository.search(param.query)
    }

    data class Param(
        val query: String
    )
}