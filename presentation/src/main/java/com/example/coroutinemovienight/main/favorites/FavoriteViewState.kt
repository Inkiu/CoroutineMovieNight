package com.example.coroutinemovienight.main.favorites

import com.example.coroutinemovienight.models.Movie

data class FavoriteViewState(
    val showLoading: Boolean = false,
    val movies: List<Movie> = emptyList()
)