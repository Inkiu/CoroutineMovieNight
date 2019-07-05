package com.example.coroutinemovienight.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    @Singleton
    @ApplicationContext
    abstract fun bindsApplicationContext(application: Application): Context
}