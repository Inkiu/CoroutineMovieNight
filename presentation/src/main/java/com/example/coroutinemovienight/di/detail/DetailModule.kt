package com.example.coroutinemovienight.di.detail

import android.content.Context
import com.example.coroutinemovienight.common.GlideImageLoader
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.detail.MovieDetailActivity
import com.example.coroutinemovienight.di.ActivityContext
import com.example.coroutinemovienight.di.PerActivity
import com.example.domain.MovieRepository
import com.example.domain.usecases.GetMovieDetail
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DetailModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideGetPopularMovie(movieRepository: MovieRepository) = GetMovieDetail(movieRepository)
    }

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindsActivityContext(activity: MovieDetailActivity): Context

    @Binds
    @PerActivity
    abstract fun bindsImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}