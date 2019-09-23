package com.example.coroutinemovienight.di

import android.app.Application
import android.content.Context
import com.example.coroutinemovienight.App
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    @Singleton
    @Named("ApplicationContext")
    abstract fun bindsApplicationContext(application: Application): Context
}