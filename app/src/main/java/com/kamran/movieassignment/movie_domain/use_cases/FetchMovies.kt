package com.kamran.movieassignment.movie_domain.use_cases

import com.kamran.movieassignment.movie_domain.repository.MovieRepository

class FetchMovies(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.fetchTrendingMovies()
}
