package com.example.coroutinemovienight.main.favorites

import androidx.lifecycle.MutableLiveData
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.NonNullMutableLiveData
import com.example.coroutinemovienight.common.SingleLiveEvent
import com.example.coroutinemovienight.main.popular.PopularViewState
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.usecases.GetFavoriteMovies
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteMovies: GetFavoriteMovies,
    private val mapper: MovieEntityMovieMapper
) : BaseViewModel() {

    val viewState: NonNullMutableLiveData<FavoriteViewState> = NonNullMutableLiveData(FavoriteViewState())
    val errorState: MutableLiveData<Throwable?> = SingleLiveEvent()

    override fun onInitialAttached() {
        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        launch {
            viewState.value = viewState.value.copy(showLoading = true)
            val moviesResult = runCatching {
                getFavoriteMovies(Unit)
                    .map { mapper.mapFrom(it) }
            }

            if (moviesResult.isSuccess) {
                viewState.value = FavoriteViewState(false, moviesResult.getOrDefault(emptyList()))
            } else {
                viewState.value = viewState.value.copy(showLoading = false)
                errorState.value = moviesResult.exceptionOrNull()
            }
        }
    }
}