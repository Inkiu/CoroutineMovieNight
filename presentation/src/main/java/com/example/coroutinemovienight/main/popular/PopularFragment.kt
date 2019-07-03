package com.example.coroutinemovienight.main.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.coroutinemovienight.R
import com.example.domain.usecases.GetPopularMovies
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularFragment : DaggerFragment() {

    @Inject lateinit var getPopularMovies: GetPopularMovies

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            getPopularMovies(Unit).forEach { Log.d("tmpLog", "onViewCreated: ${it}") }
        }
    }
}
