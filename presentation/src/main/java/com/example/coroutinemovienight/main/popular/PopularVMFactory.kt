package com.example.coroutinemovienight.main.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinemovienight.di.PerFragment
import com.example.domain.usecases.GetPopularMovies
import javax.inject.Inject

@PerFragment
class PopularVMFactory @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularViewModel(getPopularMovies) as T
    }
}