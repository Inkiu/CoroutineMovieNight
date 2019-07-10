package com.example.coroutinemovienight.di

import android.app.Application
import com.example.coroutinemovienight.App
import com.example.data.di.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        DataModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        @BindsInstance
        fun app(application: Application): Builder

        @BindsInstance
        fun apiKey(@Named("api_key") key: String): Builder

        @BindsInstance
        fun baseUrl(@Named("base_url") baseUrl: String): Builder

        fun build(): AppComponent
    }

}