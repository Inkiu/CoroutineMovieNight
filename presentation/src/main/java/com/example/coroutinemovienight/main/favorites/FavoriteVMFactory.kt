package com.example.coroutinemovienight.main.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinemovienight.di.PerFragment
import com.example.coroutinemovienight.models.mappers.MovieEntityMovieMapper
import com.example.domain.usecases.GetFavoriteMovies
import javax.inject.Inject

@PerFragment
class FavoriteVMFactory @Inject constructor(
    private val getFavoriteMovies: GetFavoriteMovies,
    private val mapper: MovieEntityMovieMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(getFavoriteMovies, mapper) as T
    }

}