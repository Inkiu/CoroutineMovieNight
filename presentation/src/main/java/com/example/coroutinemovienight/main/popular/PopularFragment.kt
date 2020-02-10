package com.example.coroutinemovienight.main.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.BaseFragment
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject

class PopularFragment : BaseFragment() {

    @Inject lateinit var vmFactory: PopularVMFactory
    @Inject lateinit var imageLoader: ImageLoader

    private val adapter: PopularAdapter by lazy {
        PopularAdapter(imageLoader) { movie, _ -> navigateMovieDetail(movie) }
    }
    private val viewModel: PopularViewModel by lazy {
        ViewModelProvider(this, vmFactory).get(PopularViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularMoviesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        popularMoviesRecyclerView.adapter = adapter
        popularSwipeRefresh.setOnRefreshListener {
            viewModel.loadPopularMovies()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this.viewLifecycleOwner, Observer { handleState(it) })
        viewModel.errorState.observe(this.viewLifecycleOwner, Observer { handleError(it) })
    }

    private fun handleState(viewState: PopularViewState) {
        popularMovieProgress.visibility = if (viewState.showLoading) View.VISIBLE else View.INVISIBLE
        popularSwipeRefresh.isRefreshing = viewState.showLoading
        adapter.replaceMovies(viewState.movies)
    }

    private fun handleError(throwable: Throwable?) {
        throwable?.let {
            Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
        }
    }
}
