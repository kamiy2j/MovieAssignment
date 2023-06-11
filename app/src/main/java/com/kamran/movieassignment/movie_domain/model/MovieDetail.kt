package com.kamran.movieassignment.movie_domain.model

data class MovieDetail(
    val adult: Boolean = false,
    val id: Int = 0,
    val posterPath: String = "",
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val genres: String = "",
    val runtime: String = ""
)