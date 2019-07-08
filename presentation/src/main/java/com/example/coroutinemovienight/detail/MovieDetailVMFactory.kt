package com.example.coroutinemovienight.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.usecases.GetMovieDetail
import javax.inject.Inject

class MovieDetailVMFactory @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val movieMapper: MovieEntityMovieMapper
) : ViewModelProvider.Factory {

    var movieId = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(
            getMovieDetail,
            movieMapper,
            movieId
        ) as T
    }
}