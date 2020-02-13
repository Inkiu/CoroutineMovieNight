package com.example.domain

import com.example.domain.usecases.*
import com.example.domain.utils.DomainTestUtils
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.Assert.assertThat
import org.mockito.Mockito.*
import java.lang.RuntimeException

class UseCasesTests {

    @Test
    fun test01_getMovieDetailById_returns_expected_values() = runBlockingTest {
        val movieEntity = DomainTestUtils.getTestMovieEntity(100)
        val movieRepository = mock(MovieRepository::class.java)
        val getMovieDetail = GetMovieDetail(movieRepository)

        `when`(movieRepository.getMovieDetail(100)).thenReturn(movieEntity)

        val result = getMovieDetail(GetMovieDetail.Param(100))
        assertThat(result, `is`(notNullValue()))
        assertThat(result.id, equalTo(movieEntity.id))
    }

    @Test
    fun test02_getPopularMovies_returns_expected_value() = runBlockingTest {
        val movieRepository = mock(MovieRepository::class.java)
        val favoriteRepository = mock(FavoriteMovieRepository::class.java)
        val getPopularMovies = GetPopularMovies(movieRepository, favoriteRepository)

        `when`(favoriteRepository.getAll()).thenReturn(DomainTestUtils.generateMovieEntityList().subList(1, 3)) // 2
        `when`(movieRepository.getPopularMovies()).thenReturn(DomainTestUtils.generateMovieEntityFlow()) // 5

        launch {
            getPopularMovies(Unit)
                .collect { result ->
                    assertThat(result.size, equalTo(7))
                }
        }.join()
    }

    @Test
    fun test03_getPopularMovies_returns_empty_when_no_result() = runBlockingTest {
        val movieRepository = mock(MovieRepository::class.java)
        val favoriteRepository = mock(FavoriteMovieRepository::class.java)
        val getPopularMovies = GetPopularMovies(movieRepository, favoriteRepository)

        `when`(favoriteRepository.getAll()).thenReturn(emptyList())
        `when`(movieRepository.getPopularMovies()).thenReturn(flow { emit(emptyList()) })

        launch {
            getPopularMovies(Unit)
                .collect { result ->
                    assert(result.isEmpty())
                }
        }.join()
    }

    @Test
    fun test04_checkFavoriteStatus() = runBlockingTest {
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
    fun test05_get_favorite_movies_after_save_favorite_movie() = runBlockingTest {
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
    fun test_06_removeFavoriteMovie_returns_expected_state() = runBlockingTest {
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
    fun test07_searchMovies_returns_expected_values() = runBlockingTest {
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