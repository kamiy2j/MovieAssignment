package com.kamran.movieassignment.movie_data.remote.mapper

import com.kamran.movieassignment.core.AppConstants
import com.kamran.movieassignment.core.utils.toGenreString
import com.kamran.movieassignment.core.utils.toHourMinFormat
import com.kamran.movieassignment.movie_data.remote.dto.MovieDetailDto
import com.kamran.movieassignment.movie_domain.model.MovieDetail

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id ?: 0,
        title = title ?: "",
        posterPath = AppConstants.POSTER_IMAGE_LARGE_SIZE_URL + poster_path,
        adult = adult ?: false,
        overview = overview ?: "",
        releaseDate = release_date ?: "No Information",
        genres = genres.toGenreString(),
        runtime = runtime.toHourMinFormat()
    )
}