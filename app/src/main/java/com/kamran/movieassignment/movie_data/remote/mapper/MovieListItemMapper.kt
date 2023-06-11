package com.kamran.movieassignment.movie_data.remote.mapper

import com.kamran.movieassignment.core.AppConstants.POSTER_IMAGE_SMALL_SIZE_URL
import com.kamran.movieassignment.movie_data.remote.dto.MovieListItem
import com.kamran.movieassignment.movie_domain.model.MovieItem

fun MovieListItem.toMovieItem(): MovieItem {
    return MovieItem(
        id = id ?: 0,
        title = title ?: "",
        posterPath = POSTER_IMAGE_SMALL_SIZE_URL + poster_path,
        adult = adult ?: false
    )
}

