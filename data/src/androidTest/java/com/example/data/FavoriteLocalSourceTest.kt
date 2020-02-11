package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.data.datasource.FavoriteLocalDataSource
import com.example.data.db.FavoriteMovieDatabase
import com.example.data.TestsUtils.Companion.generateMovieDataList
import com.example.data.entities.MovieData
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItems
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FavoriteLocalSourceTest {

    private lateinit var database: FavoriteMovieDatabase
    private lateinit var dataSource: FavoriteLocalDataSource

    private lateinit var movieDataList: List<MovieData>
    private lateinit var pickedMovieData: MovieData

    @Before
    fun before() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.databaseBuilder(context, FavoriteMovieDatabase::class.java, "test_favorite_db").build()
        dataSource = FavoriteLocalDataSource(database)

        movieDataList = generateMovieDataList(10)
        pickedMovieData = movieDataList[5]
    }

    @Test
    fun test01_read_saved_data_after_save_favorite_movies() = runBlocking {
        dataSource.save(movieDataList)
        assertEquals(movieDataList.size, dataSource.getAll().size)

        assertEquals(pickedMovieData, dataSource.get(pickedMovieData.id))
    }

    @Test
    fun test02_not_empty_search_movies() = runBlocking {
        assertEquals(movieDataList.size, dataSource.getAll().size)
        val keyword = pickedMovieData.title.substring(2, 5)
        val dataSourceResult = dataSource.search(keyword)
        assertThat(dataSourceResult, hasItems(pickedMovieData))
    }

    @Test
    fun test03_zero_result_when_all_favorite_removed() = runBlocking {
        assertEquals(movieDataList.size, dataSource.getAll().size)

        dataSource.remove(pickedMovieData.id)
        assertEquals(movieDataList.size - 1, dataSource.getAll().size)

        dataSource.removeAll()
        assertEquals(0, dataSource.getAll().size)
        assertTrue(dataSource.count() == 0)
    }

}