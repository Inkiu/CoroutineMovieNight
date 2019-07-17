package com.example.coroutinemovienight.di.main

import android.content.Context
import com.example.coroutinemovienight.common.GlideImageLoader
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.di.ActivityContext
import com.example.coroutinemovienight.di.PerActivity
import com.example.coroutinemovienight.di.PerFragment
import com.example.coroutinemovienight.main.MainActivity
import com.example.coroutinemovienight.main.favorites.FavoriteFragment
import com.example.coroutinemovienight.main.popular.PopularFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [PopularModule::class])
    @PerFragment
    abstract fun popularFragment(): PopularFragment

    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    @PerFragment
    abstract fun favoriteFragment(): FavoriteFragment

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindsActivityContext(activity: MainActivity): Context

    @Binds
    @PerActivity
    abstract fun bindsImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}