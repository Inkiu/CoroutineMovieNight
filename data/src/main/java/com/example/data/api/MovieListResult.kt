package com.example.data.api

import com.example.data.entities.MovieData
import com.google.gson.annotations.SerializedName

class MovieListResult {

    var page: Int = 0
    @SerializedName("results")
    lateinit var movies: List<MovieData>
}