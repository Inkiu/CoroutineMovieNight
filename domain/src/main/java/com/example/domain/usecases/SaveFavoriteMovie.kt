package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository
import com.example.domain.entities.MovieEntity

class SaveFavoriteMovie(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : UseCase<SaveFavoriteMovie.Param, Boolean> {

    override suspend fun invoke(param: Param): Boolean {
        val result = runCatching { favoriteMovieRepository.save(param.movie) }
        return result.isSuccess
    }

    data class Param(
        val movie: MovieEntity
    )
}