package com.example.data

import com.example.data.TestsUtils.Companion.generateDetailDataList
import com.example.data.TestsUtils.Companion.getMockedMovieData
import com.example.data.mappers.DetailsDataMovieEntityMapper
import com.example.data.mappers.MovieDataEntityMapper
import com.example.data.mappers.MovieEntityDataMapper
import com.example.domain.entities.VideoEntity
import com.example.domain.utils.DomainTestUtils
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Yossi Segev on 20/01/2018.
 */
class MapperTest {
    @Test
    fun testMappingMovieDataToMovieEntityReturnsExpectedResult() {
        val movieData = getMockedMovieData(4242)
        val mapper = MovieDataEntityMapper()
        val movieEntity = mapper.mapFrom(movieData)

        assertEquals(movieEntity.id, movieData.id)
        assertEquals(movieEntity.title, movieData.title)
        assertEquals(movieEntity.originalTitle, movieData.originalTitle)
        assertEquals(movieEntity.adult, movieData.adult)
        assertEquals(movieEntity.backdropPath, movieData.backdropPath)
        assertEquals(movieEntity.releaseDate, movieData.releaseDate)
        assertEquals(movieEntity.popularity, movieData.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, movieData.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, movieData.voteCount)
        assertEquals(movieEntity.posterPath, movieData.posterPath)
        assertEquals(movieEntity.originalLanguage, movieData.originalLanguage)
        assertEquals(movieEntity.overview, movieData.overview)
    }

    @Test
    fun testMappingDetailDataToMovieEntityReturnsExpectedResult() {

        val mapper = DetailsDataMovieEntityMapper()

        generateDetailDataList(100).forEach { detailsData ->
            val movieEntity = mapper.mapFrom(detailsData)

            assertEquals(movieEntity.id, detailsData.id)
            assertEquals(movieEntity.title, detailsData.title)
            assertEquals(movieEntity.originalTitle, detailsData.originalTitle)
            assertEquals(movieEntity.adult, detailsData.adult)
            assertEquals(movieEntity.backdropPath, detailsData.backdropPath)
            assertEquals(movieEntity.releaseDate, detailsData.releaseDate)
            assertEquals(movieEntity.popularity, detailsData.popularity, 0.0)
            assertEquals(movieEntity.voteAverage, detailsData.voteAverage, 0.0)
            assertEquals(movieEntity.voteCount, detailsData.voteCount)
            assertEquals(movieEntity.video, detailsData.video)
            assertEquals(movieEntity.posterPath, detailsData.posterPath)
            assertEquals(movieEntity.originalLanguage, detailsData.originalLanguage)
            assertEquals(movieEntity.overview, detailsData.overview)

            val onlyYoutubeDetailVideo = detailsData.videos!!.results!!.filter {
                it.type == VideoEntity.TYPE_TRAILER && it.site == VideoEntity.SOURCE_YOUTUBE
            }
            assertEquals(movieEntity.details?.videos?.size, onlyYoutubeDetailVideo.size)
            if (movieEntity.details?.videos?.isNotEmpty() == true) {
                assertEquals(
                    movieEntity.details?.videos?.get(0)?.youtubeKey,
                    onlyYoutubeDetailVideo[0].key
                )
                assertEquals(
                    movieEntity.details?.videos?.get(0)?.id,
                    onlyYoutubeDetailVideo[0].id
                )
                assertEquals(
                    movieEntity.details?.videos?.get(0)?.name,
                    onlyYoutubeDetailVideo[0].name
                )
            }

            assertEquals(movieEntity.details?.reviews?.size, detailsData.reviews!!.results!!.size)
            assertEquals(movieEntity.details?.reviews?.get(0)?.id, detailsData.reviews!!.results!![0].id)
            assertEquals(movieEntity.details?.reviews?.get(0)?.author, detailsData.reviews!!.results!![0].author)
            assertEquals(movieEntity.details?.reviews?.get(0)?.content, detailsData.reviews!!.results!![0].content)

            assertEquals(movieEntity.details?.genres?.size, detailsData.genres!!.size)
            assertEquals(movieEntity.details?.genres?.get(0)?.id, detailsData.genres!![0].id)
            assertEquals(movieEntity.details?.genres?.get(0)?.name, detailsData.genres!![0].name)

            assertEquals(movieEntity.details?.tagline, detailsData.tagline)
            assertEquals(movieEntity.details?.runtime, detailsData.runtime)
            assertEquals(movieEntity.details?.revenue, detailsData.revenue)
            assertEquals(movieEntity.details?.imdbId, detailsData.imdbId)
            assertEquals(movieEntity.details?.homepage, detailsData.homepage)
            assertEquals(movieEntity.details?.budget, detailsData.budget)
            assertEquals(movieEntity.details?.overview, detailsData.overview)
        }
    }

    @Test
    fun testMappingMovieEntityToMovieReturnsExpectedResult() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(2)
        val movieEntityDataMapper = MovieEntityDataMapper()
        val movieData = movieEntityDataMapper.mapFrom(movieEntity)

        assertEquals(movieEntity.id, movieData.id)
        assertEquals(movieEntity.title, movieData.title)
        assertEquals(movieEntity.originalTitle, movieData.originalTitle)
        assertEquals(movieEntity.adult, movieData.adult)
        assertEquals(movieEntity.backdropPath, movieData.backdropPath)
        assertEquals(movieEntity.releaseDate, movieData.releaseDate)
        assertEquals(movieEntity.popularity, movieData.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, movieData.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, movieData.voteCount)
        assertEquals(movieEntity.posterPath, movieData.posterPath)
        assertEquals(movieEntity.originalLanguage, movieData.originalLanguage)
        assertEquals(movieEntity.overview, movieData.overview)
    }
}