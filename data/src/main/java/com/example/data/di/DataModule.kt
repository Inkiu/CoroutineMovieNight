package com.example.data.di

import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
class DataModule