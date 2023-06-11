package com.kamran.movieassignment.movie_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kamran.movieassignment.core.AppConstants.MOVIE_LIST_PAGE_SIZE
import com.kamran.movieassignment.movie_data.remote.MovieDBApi
import com.kamran.movieassignment.movie_data.remote.mapper.toMovieDetail
import com.kamran.movieassignment.movie_domain.model.MovieDetail
import com.kamran.movieassignment.movie_domain.repository.MovieRepository

open class MovieRepositoryImpl(
    private val api: MovieDBApi
) : MovieRepository {

    override fun fetchTrendingMovies() = Pager(
        config = PagingConfig(
            pageSize = MOVIE_LIST_PAGE_SIZE,
        ),
        pagingSourceFactory = {
            MoviePagingSource(api)
        }
    ).flow

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val movieDetailDto = api.getMovieDetail(movieId = movieId)
            Result.success(movieDetailDto.toMovieDetail())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}