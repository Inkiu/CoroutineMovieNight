package com.example.coroutinemovienight.di.main

import com.example.coroutinemovienight.di.PerFragment
import com.example.coroutinemovienight.di.main.popular.PopularModule
import com.example.coroutinemovienight.main.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @ContributesAndroidInjector(modules = [PopularModule::class])
    @PerFragment
    abstract fun popularFragment(): PopularFragment
}