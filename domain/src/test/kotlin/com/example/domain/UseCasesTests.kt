package com.example.domain

import com.example.domain.usecases.*
import com.example.domain.utils.DomainTestUtils
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.Assert.assertThat
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.lang.RuntimeException

class UseCasesTests {

    @Test
    fun getMovieDetailById() = runBlockingTest {
        val movieEntity = DomainTestUtils.getTestMovieEntity(100)
        val movieRepository = mock(MovieRepository::class.java)
        val getMovieDetail = GetMovieDetail(movieRepository)

        `when`(movieRepository.getMovie(100)).thenReturn(movieEntity)

        val result = getMovieDetail(GetMovieDetail.Param(100))
        assertThat(result, `is`(notNullValue()))
        assertThat(result.id, equalTo(movieEntity.id))
    }

    @Test
    fun getPopularMovies() = runBlockingTest {
        val movieRepository = mock(MovieRepository::class.java)
        val getPopularMovies = GetPopularMovies(movieRepository)

        `when`(movieRepository.getPopularMovies()).thenReturn(DomainTestUtils.generateMovieEntityList())

        val result = getPopularMovies(Unit)
        assertThat(result.size, equalTo(5))
    }

    @Test
    fun getPopularMoviesNoResultsReturnsEmpty() = runBlockingTest {
        val movieRepository = mock(MovieRepository::class.java)
        val getPopularMovies = GetPopularMovies(movieRepository)

        `when`(movieRepository.getPopularMovies()).thenReturn(emptyList())

        val result = getPopularMovies(Unit)
        assert(result.isEmpty())
    }

    @Test
    fun checkFavoriteStatus() = runBlockingTest {
        val favoriteMovieRepository = mock(FavoriteMovieRepository::class.java)
        val checkFavoriteStatus = CheckFavoriteStatus(favoriteMovieRepository)

        `when`(favoriteMovieRepository.get(99)).thenReturn(null)
        `when`(favoriteMovieRepository.get(98)).thenReturn(DomainTestUtils.getTestMovieEntity(98))

        val result1 = checkFavoriteStatus(CheckFavoriteStatus.Param(99))
        assertThat(result1, `is`(false))

        val result2 = checkFavoriteStatus(CheckFavoriteStatus.Param(98))
        assertThat(result2, `is`(true))
    }

    @Test
    fun saveAndGetFavoriteMovie() = runBlockingTest {
        val favoriteMovieRepository = mock(FavoriteMovieRepository::class.java)
        val saveFavoriteMovie = SaveFavoriteMovie(favoriteMovieRepository)
        val getFavoriteMovie = GetFavoriteMovies(favoriteMovieRepository)

        // empty
        `when`(favoriteMovieRepository.getAll()).thenReturn(emptyList())
        assert(getFavoriteMovie(Unit).isEmpty())

        // save
        val savingMovie = DomainTestUtils.getTestMovieEntity(100)
        `when`(favoriteMovieRepository.save(savingMovie)).thenThrow(RuntimeException("Test Exception"))
        saveFavoriteMovie(SaveFavoriteMovie.Param(savingMovie)).also {
            assertThat(it, `is`(false))
        }

        // getting
        `when`(favoriteMovieRepository.getAll()).thenReturn(listOf(savingMovie))
        getFavoriteMovie(Unit).also {
            assertThat(it.size, `is`(1))
            assertThat(it.first().title, `is`(savingMovie.title))
        }
    }

    @Test
    fun removeFavoriteMovie() = runBlockingTest {
        val favoriteMovieRepository = mock(FavoriteMovieRepository::class.java)
        val removeFavoriteMovie = RemoveFavoriteMovie(favoriteMovieRepository)
        val getFavoriteMovie = GetFavoriteMovies(favoriteMovieRepository)

        // two movies
        val movie1 = DomainTestUtils.getTestMovieEntity(1)
        val movie2 = DomainTestUtils.getTestMovieEntity(2)
        `when`(favoriteMovieRepository.getAll()).thenReturn(listOf(movie1, movie2))
        assertThat(getFavoriteMovie(Unit).size, `is`(2))

        // removes
        `when`(favoriteMovieRepository.remove(3)).thenThrow(RuntimeException("Test Exception"))
        removeFavoriteMovie(RemoveFavoriteMovie.Param(3)).also {
            assertThat(it, `is`(false))
        }
        `when`(favoriteMovieRepository.remove(1)).thenReturn(Unit)
        removeFavoriteMovie(RemoveFavoriteMovie.Param(1)).also {
            assertThat(it, `is`(true))
        }

        // removed
        `when`(favoriteMovieRepository.getAll()).thenReturn(listOf(movie2))
        getFavoriteMovie(Unit).also {
            assertThat(it.size, `is`(1))
            assertThat(it.first().id, `is`(movie2.id))
        }
    }

    @Test
    fun searchMovies() = runBlockingTest {
        val movieRepository = mock(MovieRepository::class.java)
        val searchMovie = SearchMovies(movieRepository)
        `when`(movieRepository.search("test query")).thenReturn(DomainTestUtils.generateMovieEntityList())
        searchMovie(SearchMovies.Param("test query")).also {
            assertThat(it.size, greaterThan(1))
        }
        `when`(movieRepository.search(anyObject())).thenReturn(emptyList())
        searchMovie(SearchMovies.Param("test test query")).also {
            assert(it.isEmpty())
        }
    }

    private fun <T> anyObject(): T = any<T>() as T

}