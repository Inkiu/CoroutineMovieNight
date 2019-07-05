package com.example.coroutinemovienight.main.popular

import android.util.Log
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.models.Movie
import com.example.data.mappers.Mapper
import com.example.domain.entities.MovieEntity
import com.example.domain.usecases.GetPopularMovies
import kotlinx.coroutines.launch

class PopularViewModel(
    private val getPopularMovies: GetPopularMovies,
    private val mapper: Mapper<MovieEntity, Movie>
) : BaseViewModel() {
    override fun onAttached() {
        launch {
            getPopularMovies().forEach {
                Log.d("tmpLog", "onAttached: ${it.title}")
            }
        }
    }
    
    private suspend fun getPopularMovies(): List<Movie> {
        return getPopularMovies(Unit).map { mapper.mapFrom(it) }
    }
}