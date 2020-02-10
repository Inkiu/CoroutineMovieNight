package com.example.data.datasource

import com.example.data.db.FavoriteMovieDatabase
import com.example.data.entities.MovieData

class FavoriteLocalDataSource (
    database: FavoriteMovieDatabase
) {
    private val dao = database.getFavoriteMovieDao()

    suspend fun save(movieDataList: List<MovieData>) {
        dao.saveAllMovies(movieDataList)
    }

    suspend fun getAll(): List<MovieData> {
        return dao.getFavorites()
    }

    suspend fun get(movieId: Int): MovieData? {
        return dao.get(movieId)
    }

    suspend fun search(keyword: String): List<MovieData> {
        return dao.search(keyword)
    }

    suspend fun count(): Int {
        return dao.count()
    }

    suspend fun removeAll(): Int {
        return dao.clear()
    }

    suspend fun remove(movieId: Int): Boolean {
        return dao.removeMovie(movieId) > 0
    }
}