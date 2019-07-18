package com.example.coroutinemovienight.common

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            getViewModel().onInitialAttached()
        }
        getViewModel().onAttached()
    }

    abstract fun getViewModel(): BaseViewModel
}