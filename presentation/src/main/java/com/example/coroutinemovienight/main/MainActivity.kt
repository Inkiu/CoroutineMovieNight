package com.example.coroutinemovienight.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.main.favorites.FavoriteFragment
import com.example.coroutinemovienight.main.popular.PopularFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PopularFragment(), "popular")
                .commitNow()
            title = getString(R.string.popular)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == bottomNavigationView.selectedItemId) return false
        when (item.itemId) {

            R.id.action_popular -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PopularFragment(), "popular")
                    .commitNow()
                title = getString(R.string.popular)
            }

            R.id.action_favorites -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FavoriteFragment(), "favorites")
                    .commitNow()
                title = getString(R.string.my_favorites)
            }

            R.id.action_search -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, SearchFragment(), "search")
//                    .commitNow()
//                title = getString(R.string.search)
            }
        }
        return true
    }
}
