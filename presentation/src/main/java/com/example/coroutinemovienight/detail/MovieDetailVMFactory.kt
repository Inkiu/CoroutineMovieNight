package com.example.coroutinemovienight.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.usecases.GetMovieDetail
import com.example.domain.usecases.RemoveFavoriteMovie
import com.example.domain.usecases.SaveFavoriteMovie
import javax.inject.Inject

class MovieDetailVMFactory @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val saveFavoriteMovie: SaveFavoriteMovie,
    private val removeFavoriteMovie: RemoveFavoriteMovie,
    private val movieMapper: MovieEntityMovieMapper
) : ViewModelProvider.Factory {

    var movieId = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(
            getMovieDetail,
            saveFavoriteMovie,
            removeFavoriteMovie,
            movieMapper,
            movieId
        ) as T
    }
}