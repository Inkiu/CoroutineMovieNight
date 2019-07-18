package com.example.coroutinemovienight.main.search

import com.example.coroutinemovienight.models.Movie

data class SearchViewState(
    val query: String = "",
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList()
)