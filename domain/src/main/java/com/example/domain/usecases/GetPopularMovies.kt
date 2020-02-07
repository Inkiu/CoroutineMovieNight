package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetPopularMovies(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : UseCase<Unit, List<MovieEntity>> {

    override suspend fun invoke(param: Unit): List<MovieEntity> {
        return coroutineScope {
            val favorite = async { favoriteRepository.getAll() }
            val popular = async { movieRepository.getPopularMovies() }
            favorite.await() + popular.await()
        }
    }

}