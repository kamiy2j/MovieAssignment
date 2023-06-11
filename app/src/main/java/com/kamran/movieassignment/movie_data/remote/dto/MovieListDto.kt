package com.kamran.movieassignment.movie_data.remote.dto


data class MovieListDto(
    val page: Int? = 0,
    val results: List<MovieListItem> = listOf(),
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)