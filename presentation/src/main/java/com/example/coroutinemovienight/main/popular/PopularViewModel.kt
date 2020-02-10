package com.example.coroutinemovienight.main.popular

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.NonNullMutableLiveData
import com.example.coroutinemovienight.common.SingleLiveEvent
import com.example.coroutinemovienight.models.Movie
import com.example.data.mappers.Mapper
import com.example.domain.entities.MovieEntity
import com.example.domain.usecases.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularViewModel(
    private val getPopularMovies: GetPopularMovies,
    private val mapper: Mapper<MovieEntity, Movie>
) : BaseViewModel() {

    val viewState: NonNullMutableLiveData<PopularViewState> = NonNullMutableLiveData(PopularViewState())
    val errorState: MutableLiveData<Throwable?> = SingleLiveEvent()

    @ExperimentalCoroutinesApi
    override fun onInitialAttached() {
        loadPopularMovies()
    }

    @ExperimentalCoroutinesApi
    private fun loadPopularMovies() {
        launch {
            getPopularMovies(Unit)
                .map { it.map { mapper.mapFrom(it) } }
                .onEach { viewState.value = PopularViewState(false, it) }
                .handleError()
                .collect()
        }
    }

    @ExperimentalCoroutinesApi
    private fun <T> Flow<T>.handleError(): Flow<T> = catch { e ->
        errorState.value = e
    }
}