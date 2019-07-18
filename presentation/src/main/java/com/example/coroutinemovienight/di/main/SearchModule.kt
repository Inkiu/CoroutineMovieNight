package com.example.coroutinemovienight.di.main

import com.example.coroutinemovienight.di.PerFragment
import com.example.domain.MovieRepository
import com.example.domain.usecases.SearchMovies
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    @PerFragment
    fun provideSearchMovie(movieRepository: MovieRepository) = SearchMovies(movieRepository)

}