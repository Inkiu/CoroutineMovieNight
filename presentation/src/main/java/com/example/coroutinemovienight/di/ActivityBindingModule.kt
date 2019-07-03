package com.example.coroutinemovienight.di

import com.example.coroutinemovienight.di.main.MainModule
import com.example.coroutinemovienight.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainModule::class])
    @PerActivity
    abstract fun mainActivity(): MainActivity
}