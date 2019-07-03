package com.example.coroutinemovienight.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.main.popular.PopularFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PopularFragment(), "popular")
            .commitNow()
    }
}
