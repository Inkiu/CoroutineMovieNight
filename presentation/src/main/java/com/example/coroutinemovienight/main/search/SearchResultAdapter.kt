package com.example.coroutinemovienight.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.models.Movie
import kotlinx.android.synthetic.main.item_searched_movie.view.*

class SearchResultAdapter constructor(
    private val imageLoader: ImageLoader,
    private val onMovieSelected: (Movie, View) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.MovieCellViewHolder>() {

    private var movies: List<Movie> = listOf()
    var query: String? = null

    fun setResults(movies: List<Movie>, query: String?) {
        this.movies = movies
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_searched_movie,
            parent,
            false
        )
        return MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, imageLoader, onMovieSelected)
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title.text = movie.originalTitle
            rating.text = movie.voteAverage.toString()

            movie.overview?.let {
                overview.text = movie.overview
                overview.visibility = android.view.View.VISIBLE
            } ?: run {
                overview.visibility = android.view.View.GONE
            }

            movie.posterPath?.let {
                image.visibility = android.view.View.VISIBLE
                imageLoader.load(it, image)
            } ?: run { image.visibility == android.view.View.INVISIBLE }

            setOnClickListener { listener(movie, itemView) }
        }

    }

}