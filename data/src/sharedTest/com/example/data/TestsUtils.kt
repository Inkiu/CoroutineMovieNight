package com.example.data

import com.example.data.api.ReviewsResult
import com.example.data.api.VideoResult
import com.example.data.entities.*
import com.example.domain.entities.VideoEntity
import java.nio.charset.Charset
import kotlin.random.Random

class TestsUtils {

    companion object {
        private const val DEFAULT_ID = 50

        fun getMockedMovieData(id: Int? = null, title: String? = null): MovieData {
            val index = id ?: DEFAULT_ID
            return MovieData(
                    id = index,
                    title = title ?: "movie_title_$index",
                    backdropPath = "movie_backdrop_$index",
                    originalLanguage = "movie_language_$index",
                    posterPath = "movie_poster_path_$index",
                    originalTitle = "movie_original_title_$index",
                    releaseDate = "2020-02-11",
                    adult = index % 2 == 1,
                    popularity = 10.0 - (index % 10),
                    voteAverage = 10.0 - (index % 10),
                    voteCount = 100 - (index % 100)
            )
        }

        fun getMockedGenresData(id: Int? = null): GenreData {
            val index = id ?: DEFAULT_ID
            return GenreData(
                id = index,
                name = "genre_$index")
        }

        fun getMockedReviewsData(id: Int? = null): ReviewData {
            val index = id ?: DEFAULT_ID
            return ReviewData(
                id = "review_id_$index",
                author = "review_author_$index",
                content = "review_context_$index"
            )
        }

        fun getMockedVideoData(id: Int? = null): VideoData {
            val index = id ?: DEFAULT_ID
            return VideoData(
                id = "video_id_$index",
                name = "video_name_$index",
                site = if (index % 2 == 1) "Not Youtube" else VideoEntity.SOURCE_YOUTUBE,
                type = if (index % 2 == 1) "Not a trailer" else VideoEntity.TYPE_TRAILER,
                key = "video_key_$index"
            )
        }

        fun getMockedDetailData(id: Int? = null, title: String? = null): DetailsData {
            val index = id ?: 50
            val hasVideo = index % 3 == 1
            return DetailsData(
                id = index,
                title = title ?: "detail_title_$index",
                backdropPath = "detail_backdrop_$index",
                originalLanguage = "detail_language_$index",
                posterPath = "detail_poster_path_$index",
                originalTitle = "detail_original_title_$index",
                releaseDate = "1984-10-27",
                adult = index % 2 == 1,
                popularity = 10.0 - (index % 10),
                voteAverage = 10.0 - (index % 10),
                voteCount = 1000 - (index % 1000),
                budget = 10 - (index % 10),
                revenue = 100 - (index % 100),
                runtime = 200 - (index % 100),
                overview = "detail_overview_$index",
                homepage = "detail_homepage_url_$index",
                imdbId = "detail_imdb_id_$index",
                tagline = "detail_tag_line_$index",
                video = hasVideo,
                videos = VideoResult().apply {
                    results = if (hasVideo) (1 until 10 - index % 10).map { getMockedVideoData(index + it) }
                    else emptyList()
                },
                genres = (1 until 5 - index % 5).map { getMockedGenresData(index + it) },
                reviews = ReviewsResult().apply {
                    results = (1 until 100 - index % 100).map { getMockedReviewsData(index + it) }
                }
            )
        }

        fun generateMovieDataList(size: Int = 5): List<MovieData> {
            return (0 until size).map { getMockedMovieData(it) }
        }

        fun generateDetailDataList(size: Int = 5): List<DetailsData> {
            return (0 until size).map { getMockedDetailData(it) }
        }

    }

}