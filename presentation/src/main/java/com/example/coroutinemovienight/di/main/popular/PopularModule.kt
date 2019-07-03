package com.example.coroutinemovienight.di.main.popular

import dagger.Module
import dagger.Provides

@Module
class PopularModule {
    @Provides
    fun provideSampleString() = "hey!"
}