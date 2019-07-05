package com.example.coroutinemovienight.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MovieDetailVMFactory @Inject constructor(

) : ViewModelProvider.Factory {

    var movieId = 0

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel() as T
    }
}