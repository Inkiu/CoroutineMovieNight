package com.example.coroutinemovienight.main.search

import android.util.Log
import androidx.lifecycle.*
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.NonNullMutableLiveData
import com.example.coroutinemovienight.common.debounce
import com.example.coroutinemovienight.common.distinctUntilChanged
import com.example.coroutinemovienight.models.Movie
import com.example.data.mappers.Mapper
import com.example.domain.entities.MovieEntity
import com.example.domain.usecases.SearchMovies
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMovies: SearchMovies,
    private val mapper: Mapper<MovieEntity, Movie>
) : BaseViewModel(), LifecycleObserver {

    private data class LoadingState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )

    private val _queryData = NonNullMutableLiveData("")
    private val _queryBouncedData = _queryData.distinctUntilChanged().debounce(500L)
    private val _movieData = NonNullMutableLiveData(LoadingState())

    val viewState = MediatorLiveData<SearchViewState>()
    val errorState = MutableLiveData<Throwable?>()

    init {
        viewState.value = SearchViewState()
        viewState.addSource(_queryBouncedData) {
            val value = viewState.value ?: return@addSource
            queryActually(it)
            viewState.value = value.copy(query = it)
        }
        viewState.addSource(_movieData) {
            val value = viewState.value ?: return@addSource
            viewState.value = value.copy(isLoading = it.isLoading, movies = it.movies)
        }
    }

    fun onQuery(query: String) {
        _queryData.value = query
    }

    private fun queryActually(query: String) {
        if (query.isEmpty()) {
            _movieData.value = LoadingState()
            return
        }
        launch {
            _movieData.value = _movieData.value.copy(isLoading = true)
            val result = runCatching {
                searchMovies(SearchMovies.Param(query))
                    .map { mapper.mapFrom(it) }
            }
            if (result.isSuccess) {
                _movieData.value = LoadingState(false, result.getOrDefault(emptyList()))
            } else {
                _movieData.value = _movieData.value.copy(isLoading = false)
                errorState.value = result.exceptionOrNull()
            }
        }
    }

}