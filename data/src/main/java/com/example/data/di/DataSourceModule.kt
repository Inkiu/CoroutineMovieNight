package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.MovieApi
import com.example.data.datasource.FavoriteLocalDataSource
import com.example.data.datasource.MoviesLocalSource
import com.example.data.datasource.MoviesRemoteSource
import com.example.data.db.FavoriteMovieDatabase
import com.example.data.db.PopularMovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providesFavoriteLocalDataSource(
        @Named("ApplicationContext") applicationContext: Context
    ) : FavoriteLocalDataSource {
        return FavoriteLocalDataSource(
            Room.databaseBuilder(applicationContext, FavoriteMovieDatabase::class.java, "favorite_movie_db").build()
        )
    }

    @Provides
    @Singleton
    fun providesMovieLocalSource(
        @Named("ApplicationContext") applicationContext: Context
    ) : MoviesLocalSource {
        return MoviesLocalSource(
            Room.databaseBuilder(applicationContext, PopularMovieDatabase::class.java, "popular_movie_db").build())
    }

    @Provides
    @Singleton
    fun providesMovieRemoteSource(api: MovieApi) : MoviesRemoteSource = MoviesRemoteSource(api)

}