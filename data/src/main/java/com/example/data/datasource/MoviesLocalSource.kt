package com.example.data.datasource

import com.example.data.db.PopularMovieDatabase
import com.example.data.entities.DetailsData
import com.example.data.entities.MovieData

class MoviesLocalSource (
    popularDatabase: PopularMovieDatabase
) {

    private val popularDao = popularDatabase.getPopularMovieDao()

    private val detailMovies = mutableMapOf<Int, DetailsData>()

    suspend fun getPopularMovies(): List<MovieData> {
        return popularDao.getPopularMovies()
    }

    suspend fun putPopularMovies(movieEntities: List<MovieData>) {
        popularDao.saveMovies(movieEntities)
    }

    suspend fun removeAllPopularMovies(): Int {
        return popularDao.clear()
    }

    suspend fun getDetail(movieId: Int): DetailsData? {
        return detailMovies[movieId]
    }

    suspend fun putDetail(data: DetailsData) {
        detailMovies[data.id] = data.copy()
    }
}