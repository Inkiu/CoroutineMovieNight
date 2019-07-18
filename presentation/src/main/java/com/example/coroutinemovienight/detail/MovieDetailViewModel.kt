package com.example.coroutinemovienight.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.NonNullMutableLiveData
import com.example.coroutinemovienight.models.Movie
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.entities.MovieEntity
import com.example.domain.usecases.CheckFavoriteStatus
import com.example.domain.usecases.GetMovieDetail
import com.example.domain.usecases.RemoveFavoriteMovie
import com.example.domain.usecases.SaveFavoriteMovie
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetail: GetMovieDetail,
    private val saveFavoriteMovie: SaveFavoriteMovie,
    private val removeFavoriteMovie: RemoveFavoriteMovie,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val movieMapper: MovieEntityMovieMapper,
    private val movieId: Int
) : BaseViewModel() {

    val viewState: NonNullMutableLiveData<MovieDetailsViewState> = NonNullMutableLiveData(MovieDetailsViewState(isLoading = true))
    val favoriteState : NonNullMutableLiveData<Boolean> = NonNullMutableLiveData(false)

    override fun onInitialAttached() {
        launch {
            val result = runCatching { patchFavoriteState() } 
            result.getOrNull()?.let {
                favoriteState.value = it
            } ?: {
                Log.d("tmpLog", "onInitialAttached: ${result.exceptionOrNull()}")
                // Error
            }()

        }
        launch {
            val result = runCatching { patchMovieDetail() }
            result.getOrNull()?.let {
                viewState.value = viewState.value.copy(
                    isLoading = false,
                    title = it.originalTitle,
                    videos = it.details?.videos,
                    homepage = it.details?.homepage,
                    overview = it.details?.overview,
                    releaseDate = it.releaseDate,
                    votesAverage = it.voteAverage,
                    backdropUrl = it.backdropPath,
                    genres = it.details?.genres
                )
            } ?: {
                Log.d("tmpLog", "onInitialAttached: ${result.exceptionOrNull()}")
                // Error
            }()
        }
    }

    fun onToggleFavorite() {
        launch {
            val func = if (favoriteState.value) removeFavorite()
            else saveFavorite()
            if (func) favoriteState.value = !favoriteState.value
        }
    }

    private suspend fun patchMovieDetail(): Movie {
        return getMovieDetail
            .compose(GetMovieDetail.Param(movieId))
            .let { movieMapper.mapFrom(it) }
    }

    private suspend fun patchFavoriteState(): Boolean {
        return checkFavoriteStatus
            .compose(CheckFavoriteStatus.Param(movieId))
    }

    private suspend fun saveFavorite(): Boolean {
        val movieEn = getMovieDetail
            .compose(GetMovieDetail.Param(movieId))
        return saveFavoriteMovie
            .compose(SaveFavoriteMovie.Param(movieEn))
    }

    private suspend fun removeFavorite(): Boolean {
        return removeFavoriteMovie
            .compose(RemoveFavoriteMovie.Param(movieId))
    }
}