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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularViewModel(
    private val getPopularMovies: GetPopularMovies,
    private val mapper: Mapper<MovieEntity, Movie>
) : BaseViewModel() {

    val viewState: NonNullMutableLiveData<PopularViewState> = NonNullMutableLiveData(PopularViewState())
    val errorState: MutableLiveData<Throwable?> = SingleLiveEvent()

    override fun onAttached() {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        launch {
            val moviesResult = runCatching {
                getPopularMovies
                    .compose(Unit)
                    .map { mapper.mapFrom(it) }
            }

            if (moviesResult.isSuccess) {
                viewState.value = PopularViewState(false, moviesResult.getOrDefault(emptyList()))
            } else {
                viewState.value = viewState.value.copy(showLoading = false)
                errorState.value = moviesResult.exceptionOrNull()
            }
        }
    }
}