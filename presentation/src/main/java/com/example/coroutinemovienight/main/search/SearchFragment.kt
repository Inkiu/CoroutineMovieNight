package com.example.coroutinemovienight.main.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.BaseFragment
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_searched_movie.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject lateinit var vmFactory: SearchVMFactory
    @Inject lateinit var imageLoader: ImageLoader

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter(imageLoader) { movie, _ ->
            showSoftKeyboard(false)
            navigateMovieDetail(movie)
        }
    }
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(SearchViewModel::class.java)
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { /* no-op */ }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { /* no-op */ }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { viewModel.onQuery(s.toString()) }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText.addTextChangedListener(textWatcher)
        searchEditText.requestFocus()
        searchMovieRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchMovieRecyclerView.adapter = adapter
        showSoftKeyboard(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer { handleState(it) })
        viewModel.errorState.observe(this, Observer { handleError(it) })
    }

    private fun handleState(viewState: SearchViewState) {
        searchMovieProgress.visibility = if (viewState.isLoading) View.VISIBLE else View.INVISIBLE
        adapter.setResults(viewState.movies, viewState.query)
    }

    private fun handleError(throwable: Throwable?) {
        throwable?.let {
            Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showSoftKeyboard(show: Boolean) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
        } else {
            imm.hideSoftInputFromWindow(searchEditText.windowToken,0)
        }
    }
}