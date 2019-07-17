package com.example.coroutinemovienight.main.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.models.Movie
import kotlinx.android.synthetic.main.item_favorite_movie.view.*

class FavoriteAdapter(
    private val imageLoader: ImageLoader,
    private val onMovieSelected: (Movie, View) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.MovieCellViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.item_favorite_movie,
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

    fun replaceMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title.text = movie.originalTitle
            movie.posterPath?.let { imageLoader.load(it, image) }

            movie.overview?.let {
                overview.text = movie.overview
                overview.visibility = View.VISIBLE
            } ?: run {
                overview.visibility = View.GONE
            }
            setOnClickListener { listener(movie, itemView) }
        }

    }
}