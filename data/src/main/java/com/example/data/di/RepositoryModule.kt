package com.example.data.di

import com.example.data.repositories.MoviesRepositoryImpl
import com.example.domain.MovieRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsMovieRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MovieRepository

}