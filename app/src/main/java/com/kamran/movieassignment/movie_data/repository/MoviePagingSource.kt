package com.kamran.movieassignment.movie_data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kamran.movieassignment.movie_data.remote.MovieDBApi
import com.kamran.movieassignment.movie_data.remote.mapper.toMovieItem
import com.kamran.movieassignment.movie_domain.model.MovieItem

class MoviePagingSource(
    private val api: MovieDBApi
) : PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val page = params.key ?: 1
            val response = api.fetchTrendingMovies(page = page)

            LoadResult.Page(
                data = response.results.map { it.toMovieItem() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}