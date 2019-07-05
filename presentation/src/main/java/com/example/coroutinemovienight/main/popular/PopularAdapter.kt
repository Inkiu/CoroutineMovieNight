package com.example.coroutinemovienight.main.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.ImageLoader
import com.example.coroutinemovienight.models.Movie
import kotlinx.android.synthetic.main.item_popular_movie.view.*

class PopularAdapter(
    private val imageLoader: ImageLoader,
    private val onMovieSelected: (Movie, View) -> Unit
) : RecyclerView.Adapter<PopularAdapter.MovieHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_popular_movie,
            parent,
            false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, imageLoader, onMovieSelected)
    }

    fun replaceMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title.text = movie.originalTitle
            movie.posterPath?.let { imageLoader.load(it, image) }
            setOnClickListener { listener(movie, itemView) }
        }
    }
}