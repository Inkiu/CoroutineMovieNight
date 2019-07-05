package com.example.coroutinemovienight.main.popular

import com.example.coroutinemovienight.models.Movie

data class PopularViewState(
    val showLoading: Boolean = true,
    val movies: List<Movie> = emptyList()
)