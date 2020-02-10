package com.example.data.repositories

import com.example.data.datasource.FavoriteLocalDataSource
import com.example.data.mappers.MovieDataEntityMapper
import com.example.data.mappers.MovieEntityDataMapper
import com.example.domain.FavoriteMovieRepository
import com.example.domain.entities.MovieEntity
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteLocalDataSource,
    private val entityToDataMapper: MovieEntityDataMapper,
    private val dataToEntityMapper: MovieDataEntityMapper
) : FavoriteMovieRepository {

    override suspend fun save(movieEntity: MovieEntity) {
        entityToDataMapper.mapFrom(movieEntity).let {
            localDataSource.save(listOf(it))
        }
    }

    override suspend fun saveAll(moviesEntities: List<MovieEntity>) {
        localDataSource.save(
            moviesEntities.map {
                entityToDataMapper.mapFrom(it)
            }
        )
    }

    override suspend fun get(movieId: Int): MovieEntity? {
        return localDataSource.get(movieId)?.let {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun getAll(): List<MovieEntity> {
        return localDataSource.getAll().map {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun search(query: String): List<MovieEntity> {
        return localDataSource.search(query).map {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return localDataSource.count() <= 0
    }

    override suspend fun remove(movieId: Int) {
        localDataSource.remove(movieId)
    }

    override suspend fun clear() {
        localDataSource.removeAll()
    }
}