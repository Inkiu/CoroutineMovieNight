package com.example.coroutinemovienight.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinemovienight.models.Movie
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.data.mappers.Mapper
import com.example.domain.entities.MovieEntity
import com.example.domain.usecases.SearchMovies
import javax.inject.Inject

class SearchVMFactory @Inject constructor(
    private val searchMovies: SearchMovies,
    private val mapper: MovieEntityMovieMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
        = SearchViewModel(searchMovies, mapper) as T
}