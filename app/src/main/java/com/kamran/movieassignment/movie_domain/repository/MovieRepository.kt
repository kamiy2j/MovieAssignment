package com.kamran.movieassignment.movie_domain.repository

import androidx.paging.PagingData
import com.kamran.movieassignment.movie_domain.model.MovieDetail
import com.kamran.movieassignment.movie_domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchTrendingMovies(): Flow<PagingData<MovieItem>>

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
}