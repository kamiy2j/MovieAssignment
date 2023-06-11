package com.kamran.movieassignment.movie_presentation.detail

import com.kamran.movieassignment.movie_domain.model.MovieDetail
import com.kamran.movieassignment.movie_domain.model.MovieItem

data class MovieDetailState (
    val isLoading: Boolean = false,
    val isLoadingFailed: Boolean = false,
    val movieDetail: MovieDetail = MovieDetail()
)