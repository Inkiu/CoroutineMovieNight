package com.example.coroutinemovienight.di.detail

import android.content.Context
import com.example.coroutinemovienight.common.GlideImageLoader
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.detail.MovieDetailActivity
import com.example.coroutinemovienight.di.PerActivity
import com.example.domain.FavoriteMovieRepository
import com.example.domain.MovieRepository
import com.example.domain.usecases.CheckFavoriteStatus
import com.example.domain.usecases.GetMovieDetail
import com.example.domain.usecases.RemoveFavoriteMovie
import com.example.domain.usecases.SaveFavoriteMovie
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class DetailModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideGetPopularMovie(movieRepository: MovieRepository) = GetMovieDetail(movieRepository)

        @JvmStatic
        @Provides
        @PerActivity
        fun provideSaveFavoriteMovie(favoriteMovieRepository: FavoriteMovieRepository) = SaveFavoriteMovie(favoriteMovieRepository)

        @JvmStatic
        @Provides
        @PerActivity
        fun provideRemoveFavoriteMovie(favoriteMovieRepository: FavoriteMovieRepository) = RemoveFavoriteMovie(favoriteMovieRepository)

        @JvmStatic
        @Provides
        @PerActivity
        fun provideCheckFavoroteMovie(favoriteMovieRepository: FavoriteMovieRepository) = CheckFavoriteStatus(favoriteMovieRepository)

        @JvmStatic
        @Provides
        @Named("movie_id")
        @PerActivity
        fun provideMovieId(detailActivity: MovieDetailActivity) = detailActivity.intent.getIntExtra(MovieDetailActivity.MOVIE_ID, 0)
    }

    @Binds
    @PerActivity
    @Named("ActivityContext")
    abstract fun bindsActivityContext(activity: MovieDetailActivity): Context

    @Binds
    @PerActivity
    abstract fun bindsImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}