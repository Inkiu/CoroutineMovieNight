package com.example.coroutinemovienight.detail

import com.example.coroutinemovienight.models.Video

data class MovieDetailsViewState(
        var isLoading: Boolean = true,
        var title: String? = null,
        var overview: String? = null,
        var videos: List<Video>? = null,
        var homepage: String? = null,
        var releaseDate: String? = null,
        var votesAverage: Double? = null,
        var backdropUrl: String? = null,
        var genres: List<String>? = null
)