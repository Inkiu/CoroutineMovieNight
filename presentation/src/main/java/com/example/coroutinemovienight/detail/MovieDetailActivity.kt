package com.example.coroutinemovienight.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.common.SimpleTransitionEndedCallback
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : DaggerAppCompatActivity() {

    @Inject lateinit var vmFactory: MovieDetailVMFactory
    @Inject lateinit var imageLoader: ImageLoader
    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(MovieDetailViewModel::class.java)
    }

    companion object {
        private const val MOVIE_ID = "extra_movie_id"
        private const val MOVIE_POSTER_URL: String = "extra_movie_poster_url"

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
        postponeEnterTransition()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN

        vmFactory.movieId = intent.getIntExtra(MOVIE_ID, 0)

        intent.getStringExtra(MOVIE_POSTER_URL)?.let {
            imageLoader.load(it, details_poster) { startPostponedEnterTransition() }
        } ?: run { startPostponedEnterTransition() }

        window.sharedElementEnterTransition.addListener(SimpleTransitionEndedCallback { observe() })

        // If we don't have any entering transition
        if (savedInstanceState != null) {
            observe()
        } else {
//            viewModel.getMovieDetails()
        }
    }

    private fun observe() {

    }

}
