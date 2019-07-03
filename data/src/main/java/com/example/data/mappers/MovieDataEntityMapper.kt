package com.example.data.mappers

import com.example.data.entities.MovieData
import com.example.domain.entities.MovieEntity

class MovieDataEntityMapper : Mapper<MovieData, MovieEntity> {

    override fun mapFrom(from: MovieData): MovieEntity {
        return MovieEntity(
                id = from.id,
                voteCount = from.voteCount,
                voteAverage = from.voteAverage,
                popularity = from.popularity,
                adult = from.adult,
                title = from.title,
                posterPath = from.posterPath,
                originalLanguage = from.originalLanguage,
                backdropPath = from.backdropPath,
                originalTitle = from.originalTitle,
                releaseDate = from.releaseDate,
                overview = from.overview
        )
    }
}
