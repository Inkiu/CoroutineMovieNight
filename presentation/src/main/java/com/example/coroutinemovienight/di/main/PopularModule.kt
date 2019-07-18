package com.example.coroutinemovienight.di.main

import com.example.coroutinemovienight.di.PerFragment
import com.example.domain.MovieRepository
import com.example.domain.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides

@Module
class PopularModule {
    @Provides
    @PerFragment
    fun provideGetPopularMovie(movieRepository: MovieRepository) = GetPopularMovies(movieRepository)
}