package com.example.coroutinemovienight.main.popular

import android.util.Log
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.domain.usecases.GetPopularMovies
import kotlinx.coroutines.launch

class PopularViewModel (
    private val getPopularMovies: GetPopularMovies
) : BaseViewModel() {
    override fun onAttached() {
        launch {
            getPopularMovies(Unit).forEach {
                Log.d("tmpLog", "onAttached: ${it.title}")
            }
        }
    }
}