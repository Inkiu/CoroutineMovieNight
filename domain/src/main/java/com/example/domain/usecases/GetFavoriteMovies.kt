package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository
import com.example.domain.entities.MovieEntity

class GetFavoriteMovies(
    private val favoriteMovieRepository: FavoriteMovieRepository
) : UseCase<Unit, List<MovieEntity>> {
    override suspend fun invoke(param: Unit): List<MovieEntity> {
        return favoriteMovieRepository.getAll()
    }
}