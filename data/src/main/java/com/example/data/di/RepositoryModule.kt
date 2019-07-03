package com.example.data.di

import com.example.data.api.MovieApi
import com.example.data.repositories.MoviesRemoteSource
import com.example.data.repositories.MoviesRepositoryImpl
import com.example.domain.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi
    ): MovieRepository {
        val remoteSource = MoviesRemoteSource(movieApi)
        return MoviesRepositoryImpl(remoteSource)
    }
}