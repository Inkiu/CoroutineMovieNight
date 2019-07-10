package com.example.coroutinemovienight.detail

import androidx.lifecycle.MutableLiveData
import com.example.coroutinemovienight.common.BaseViewModel
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

    val viewState: MutableLiveData<MovieDetailsViewState> = MutableLiveData()
    val favoriteState : MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewState.value = MovieDetailsViewState(isLoading = true)
        favoriteState.value = false
    }


    override fun onAttached() {
        /* no-op */
    }

    fun getMovieDetails() {
        launch {
            val favoriteDef = async {
                runCatching { patchFavoriteState() }
            }
            val detailDef = async {
                runCatching { patchMovieDetail() }
            }

            val favoriteResult = favoriteDef.await().getOrNull()
            favoriteResult?.let {
                favoriteState.value = it
            } ?: {
                // Error
            }()

            val detailResult = detailDef.await().getOrNull()
            detailResult?.let {
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    title = it.originalTitle,
                    videos = it.details?.videos,
                    homepage = it.details?.homepage,
                    overview = it.details?.overview,
                    releaseDate = it.releaseDate,
                    votesAverage = it.voteAverage,
                    backdropUrl = it.backdropPath,
                    genres = it.details?.genres)
            } ?: {
                // Error
            }()
        }
    }

    fun onToggleFavorite() {
        launch {
            val isFavorite = favoriteState.value == true
            val func = if (isFavorite) removeFavorite()
            else saveFavorite()
            if (func) favoriteState.value = !isFavorite
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