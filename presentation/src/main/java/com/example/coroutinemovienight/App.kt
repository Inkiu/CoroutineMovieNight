package com.example.coroutinemovienight

import com.example.coroutinemovienight.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .app(this)
            .apiKey(getString(R.string.movie_api_key))
            .baseUrl(getString(R.string.api_base_url))
            .build()
    }

}