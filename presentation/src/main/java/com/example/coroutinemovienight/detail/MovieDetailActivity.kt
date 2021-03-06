package com.example.coroutinemovienight.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.BaseActivity
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.ImageLoader
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.details_overview_section.*
import kotlinx.android.synthetic.main.details_video_section.*
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var vmFactory: MovieDetailVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader
    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(MovieDetailViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    companion object {
        const val MOVIE_ID = "extra_movie_id"
        const val MOVIE_POSTER_URL: String = "extra_movie_poster_url"

        fun newIntent(context: Context, movieId: Int, posterUrl: String?): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(MOVIE_POSTER_URL, posterUrl)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN

//        vmFactory.movieId = intent.getIntExtra(MOVIE_ID, 0)

        intent.getStringExtra(MOVIE_POSTER_URL)?.let {
            imageLoader.load(it, details_poster) { }
        } ?: run { }

        observe()
        setupViewFunctions()
    }

    private fun setupViewFunctions() {
        details_favorite_fab.setOnClickListener {
            viewModel.onToggleFavorite()
        }
    }

    private fun observe() {
        viewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
        viewModel.favoriteState.observe(this, Observer { state -> handleFavoriteState(state) })
    }

    private fun handleViewState(state: MovieDetailsViewState) {
        details_title.text = state.title
        details_overview.text = state.overview
        details_release_date.text = String.format(getString(R.string.release_date_template, state.releaseDate))
        details_score.text = if (state.votesAverage == 0.0) getString(R.string.n_a) else state.votesAverage.toString()
        state.genres?.let { details_tags.tags = state.genres }

        val transition = Slide()
        transition.excludeTarget(details_poster, true)
        transition.duration = 750
        TransitionManager.beginDelayedTransition(details_root_view, transition)
        details_title.visibility = View.VISIBLE
        details_release_date.visibility = View.VISIBLE
        details_score.visibility = View.VISIBLE
        details_release_date_layout.visibility = View.VISIBLE
        details_score_layout.visibility = View.VISIBLE
        details_overview_section.visibility = View.VISIBLE
        details_video_section.visibility = View.VISIBLE
        details_tags.visibility = View.VISIBLE

        state.backdropUrl?.let { imageLoader.load(it, details_backdrop) }

        state.videos?.let {
            val videosAdapter = VideosAdapter(it) { }
            details_videos.layoutManager = LinearLayoutManager(this)
            details_videos.adapter = videosAdapter

        } ?: run {
            details_video_section.visibility = View.GONE
        }
    }

    private fun handleFavoriteState(favorite: Boolean) {
        details_favorite_fab.visibility = View.VISIBLE
        details_favorite_fab.setImageDrawable(
            if (favorite) ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_36dp)
            else ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_36dp)
        )
    }

}
