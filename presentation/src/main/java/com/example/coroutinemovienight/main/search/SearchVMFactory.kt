package com.example.coroutinemovienight.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.SearchMovies
import javax.inject.Inject

class SearchVMFactory @Inject constructor(
    private val searchMovies: SearchMovies
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}