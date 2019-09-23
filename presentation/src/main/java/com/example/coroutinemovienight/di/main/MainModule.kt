package com.example.coroutinemovienight.di.main

import android.content.Context
import com.example.coroutinemovienight.common.GlideImageLoader
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.di.PerActivity
import com.example.coroutinemovienight.di.PerFragment
import com.example.coroutinemovienight.main.MainActivity
import com.example.coroutinemovienight.main.favorites.FavoriteFragment
import com.example.coroutinemovienight.main.popular.PopularFragment
import com.example.coroutinemovienight.main.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [PopularModule::class])
    @PerFragment
    abstract fun popularFragment(): PopularFragment

    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    @PerFragment
    abstract fun favoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    @PerFragment
    abstract fun searchFragment(): SearchFragment

    @Binds
    @PerActivity
    @Named("ActivityContext")
    abstract fun bindsActivityContext(activity: MainActivity): Context

    @Binds
    @PerActivity
    abstract fun bindsImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}