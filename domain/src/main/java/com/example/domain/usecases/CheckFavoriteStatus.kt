package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository

class CheckFavoriteStatus(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : UseCase<CheckFavoriteStatus.Param, Boolean> {

    override suspend fun invoke(param: Param): Boolean {
        val result = runCatching { favoriteMovieRepository.get(param.movieId) }
        return result.getOrNull() != null
    }

    data class Param(
        val movieId: Int
    )
}