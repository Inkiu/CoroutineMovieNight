package com.example.coroutinemovienight.detail

import androidx.lifecycle.MutableLiveData
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.models.Movie
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.usecases.GetMovieDetail
import com.example.domain.usecases.RemoveFavoriteMovie
import com.example.domain.usecases.SaveFavoriteMovie
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetail: GetMovieDetail,
    private val saveFavoriteMovie: SaveFavoriteMovie,
    private val removeFavoriteMovie: RemoveFavoriteMovie,
    private val movieMapper: MovieEntityMovieMapper,
    private val movieId: Int
) : BaseViewModel() {

    val viewState: MutableLiveData<MovieDetailsViewState> = MutableLiveData()

    init {
        viewState.value = MovieDetailsViewState(isLoading = true)
    }


    override fun onAttached() {
        /* no-op */
    }

    fun getMovieDetails() {
        launch {
            val result = runCatching {
                patchMovieDetail()
            }
            if (result.isSuccess && result.getOrNull() != null) {
                val movie = result.getOrNull()!!
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    title = movie.originalTitle,
                    videos = movie.details?.videos,
                    homepage = movie.details?.homepage,
                    overview = movie.details?.overview,
                    releaseDate = movie.releaseDate,
                    votesAverage = movie.voteAverage,
                    backdropUrl = movie.backdropPath,
                    genres = movie.details?.genres)
            } else {

            }
        }
    }

    private suspend fun patchMovieDetail(): Movie {
        return getMovieDetail
            .compose(GetMovieDetail.Param(movieId))
            .let { movieMapper.mapFrom(it) }
    }
}