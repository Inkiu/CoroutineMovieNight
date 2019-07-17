package com.example.coroutinemovienight.main.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
) : BaseViewModel() {

    private val _queryData = NonNullMutableLiveData("")

    val viewState = NonNullMutableLiveData(SearchViewState())
    val errorState = MutableLiveData<Throwable?>()

    init {
        Transformations.map(_queryData.distinctUntilChanged().debounce(500L)) {
            queryActually(it)
        }
    }

    fun onQuery(query: String) {
        _queryData.value = query
    }

    private fun queryActually(query: String) {
        launch {
            viewState.value = viewState.value.copy(isLoading = true)
            val result = runCatching {
                searchMovies
                    .compose(SearchMovies.Param(query))
                    .map { mapper.mapFrom(it) }
            }
            if (result.isSuccess) {
                viewState.value = SearchViewState(query, false, result.getOrDefault(emptyList()))
            } else {
                viewState.value = viewState.value.copy(isLoading = false)
                errorState.value = result.exceptionOrNull()
            }
        }
    }

}