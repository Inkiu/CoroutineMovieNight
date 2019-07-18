package com.example.coroutinemovienight.common

import android.os.Bundle
import com.example.coroutinemovienight.detail.MovieDetailActivity
import com.example.coroutinemovienight.models.Movie
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            getViewModel().onInitialAttached()
        }
        getViewModel().onAttached()
    }

    abstract fun getViewModel(): BaseViewModel

    protected fun navigateMovieDetail(movie: Movie) {
        startActivity(MovieDetailActivity.newIntent(requireContext(), movie.id, movie.posterPath))
    }
}