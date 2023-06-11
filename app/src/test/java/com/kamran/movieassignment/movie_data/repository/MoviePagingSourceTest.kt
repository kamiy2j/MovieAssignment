package com.kamran.movieassignment.movie_data.repository

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.kamran.movieassignment.movie_data.remote.MovieDBApi
import com.kamran.movieassignment.movie_data.remote.dto.MovieListDto
import com.kamran.movieassignment.movie_data.remote.dto.MovieListItem
import com.kamran.movieassignment.movie_data.remote.mapper.toMovieItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MoviePagingSourceTest {

    @RelaxedMockK
    lateinit var api: MovieDBApi

    lateinit var moviePagingSource: MoviePagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        moviePagingSource = MoviePagingSource(api)
    }


    @Test
    fun `reviews paging source load - failure - http error`() = runTest {
        val error = RuntimeException("404", Throwable())
        coEvery{
            api.fetchTrendingMovies(any(),any())
        } throws error
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieListDto>(error)
        assertThat(
            expectedResult
        ).isEqualTo(
            moviePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `reviews paging source load - failure - received null`() = runTest {
        coEvery{
            api.fetchTrendingMovies(any(),any())
        } throws NullPointerException()
        val expectedResult = PagingSource.LoadResult.Error<Int, MovieListDto>(NullPointerException())
        assertEquals(
            expectedResult.toString(), moviePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `reviews paging source refresh - success`()  = runTest {
        coEvery{
            api.fetchTrendingMovies(any(),any())
        } returns moviesResponse
        val expectedResult = PagingSource.LoadResult.Page(
            data = moviesResponse.results.map { it.toMovieItem() },
            prevKey = -1,
            nextKey = 1
        )
        assertEquals(
            expectedResult, moviePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `reviews paging source append - success`() = runTest {
        coEvery{
            api.fetchTrendingMovies(any(),any())
        } returns nextMoviesResponse
        val expectedResult = PagingSource.LoadResult.Page(
            data = moviesResponse.results.map { it.toMovieItem() },
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, moviePagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    companion object {
        val moviesResponse =
            MovieListDto(1,
                listOf(
                    MovieListItem(
                        id = 1,
                    )
                ),
                38661,
                773218)

        val nextMoviesResponse =
            MovieListDto(2,
                listOf(
                    MovieListItem(
                        id = 1,
                    )
                ),
                38661,
                773218)
    }
}