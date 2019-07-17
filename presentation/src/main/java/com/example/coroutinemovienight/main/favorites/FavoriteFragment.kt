package com.example.coroutinemovienight.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.BaseFragment
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    @Inject lateinit var vmFactory: FavoriteVMFactory
    @Inject lateinit var imageLoader: ImageLoader

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(imageLoader) { movie, _ -> navigateMovieDetail(movie) }
    }
    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(FavoriteViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoriteMoviesRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}