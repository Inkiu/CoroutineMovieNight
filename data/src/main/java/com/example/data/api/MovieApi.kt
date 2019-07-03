package com.example.data.api

import com.example.data.entities.DetailsData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{id}?append_to_response=videos,reviews")
    suspend fun getMovieDetails(@Path("id") movieId: Int): DetailsData

    @GET("movie/popular") ///movie/now_playing
    suspend fun getPopularMovies(): MovieListResult

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): MovieListResult

}