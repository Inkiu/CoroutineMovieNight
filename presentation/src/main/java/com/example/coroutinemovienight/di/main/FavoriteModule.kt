package com.example.coroutinemovienight.di.main

import com.example.coroutinemovienight.di.PerFragment
import com.example.domain.FavoriteMovieRepository
import com.example.domain.usecases.GetFavoriteMovies
import dagger.Module
import dagger.Provides

@Module
class FavoriteModule {
    @Provides
    @PerFragment
    fun provideGetFavoriteMovies(favoriteRepository: FavoriteMovieRepository) = GetFavoriteMovies(favoriteRepository)
}