package com.example.domain.usecases

import com.example.domain.FavoriteMovieRepository
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPopularMovies(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : UseCase<Unit, Flow<List<MovieEntity>>> {

    override suspend fun invoke(param: Unit): Flow<List<MovieEntity>> {
        return coroutineScope {
            val favorite = async { favoriteRepository.getAll() }
            movieRepository.getPopularMovies().map {
                favorite.await() + it
            }
        }
    }

}