package com.example.coroutinemovienight.di

import com.example.coroutinemovienight.detail.MovieDetailActivity
import com.example.coroutinemovienight.di.detail.DetailModule
import com.example.coroutinemovienight.di.main.MainModule
import com.example.coroutinemovienight.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainModule::class])
    @PerActivity
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailModule::class])
    @PerActivity
    abstract fun detailActivity(): MovieDetailActivity
}