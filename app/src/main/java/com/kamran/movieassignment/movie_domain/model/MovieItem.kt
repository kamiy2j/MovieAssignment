package com.kamran.movieassignment.movie_domain.model

data class MovieItem(
    val adult: Boolean = false,
    val id: Int = 0,
    val posterPath: String = "",
    val title: String = ""
)