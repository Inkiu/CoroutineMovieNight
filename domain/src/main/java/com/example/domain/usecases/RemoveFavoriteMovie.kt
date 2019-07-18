package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository

class RemoveFavoriteMovie(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : UseCase<RemoveFavoriteMovie.Param, Boolean> {

    override suspend fun invoke(param: Param): Boolean {
        val result = runCatching { favoriteMovieRepository.remove(param.movieId) }
        return result.isSuccess
    }

    data class Param(
        val movieId: Int
    )
}