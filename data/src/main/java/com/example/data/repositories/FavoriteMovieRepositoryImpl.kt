package com.example.data.repositories

import com.example.domain.FavoriteMovieRepository
import com.example.domain.entities.MovieEntity
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(

) : FavoriteMovieRepository {

    private val memoryCache = mutableMapOf<Int, MovieEntity>()

    override suspend fun save(movieEntity: MovieEntity) {
        memoryCache[movieEntity.id] = movieEntity
    }

    override suspend fun remove(movieId: Int) {
        memoryCache.remove(movieId)
    }
}