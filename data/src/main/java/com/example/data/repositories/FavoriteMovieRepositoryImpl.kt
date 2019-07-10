package com.example.data.repositories

import com.example.data.db.FavoriteMovieDatabase
import com.example.data.entities.MovieData
import com.example.data.mappers.Mapper
import com.example.domain.FavoriteMovieRepository
import com.example.domain.entities.MovieEntity
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    database: FavoriteMovieDatabase,
    private val entityToDataMapper: Mapper<MovieEntity, MovieData>,
    private val dataToEntityMapper: Mapper<MovieData, MovieEntity>
) : FavoriteMovieRepository {

    private val dao = database.getFavoriteMovieDao()

    override suspend fun save(movieEntity: MovieEntity) {
        entityToDataMapper.mapFrom(movieEntity).let {
            dao.saveMovie(it)
        }
    }

    override suspend fun saveAll(moviesEntities: List<MovieEntity>) {
        dao.saveAllMovies(
            moviesEntities.map {
                entityToDataMapper.mapFrom(it)
            }
        )
    }

    override suspend fun get(movieId: Int): MovieEntity? {
        return dao.get(movieId)?.let {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun getAll(): List<MovieEntity> {
        return dao.getFavorites().map {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun search(query: String): List<MovieEntity> {
        return dao.search(query).map {
            dataToEntityMapper.mapFrom(it)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return dao.getFavorites().isEmpty()
    }

    override suspend fun remove(movieId: Int) {
        dao.removeMovie(movieId)
    }

    override suspend fun clear() {
        dao.clear()
    }
}