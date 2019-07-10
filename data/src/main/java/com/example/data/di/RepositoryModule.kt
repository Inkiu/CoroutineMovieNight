package com.example.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.data.db.FavoriteMovieDatabase
import com.example.data.entities.MovieData
import com.example.data.mappers.Mapper
import com.example.data.mappers.MovieDataEntityMapper
import com.example.data.mappers.MovieEntityDataMapper
import com.example.data.repositories.FavoriteMovieRepositoryImpl
import com.example.data.repositories.MoviesRepositoryImpl
import com.example.domain.FavoriteMovieRepository
import com.example.domain.MovieRepository
import com.example.domain.entities.MovieEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Module
    companion object { // TODO Qualifier, Named 로 Context 관리
        @JvmStatic
        @Provides
        @Singleton
        fun provideFavoriteRepository(application: Application,
                                      entityDataMapper: MovieEntityDataMapper,
                                     dataEntityMapper: MovieDataEntityMapper): FavoriteMovieRepository {
            val database = Room.databaseBuilder(application, FavoriteMovieDatabase::class.java, "favorite_movie_db").build()
            return FavoriteMovieRepositoryImpl(database, entityDataMapper, dataEntityMapper)
        }
    }

    @Singleton
    @Binds
    abstract fun bindsMovieRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MovieRepository

}