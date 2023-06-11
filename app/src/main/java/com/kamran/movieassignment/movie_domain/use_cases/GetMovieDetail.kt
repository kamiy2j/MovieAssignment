package com.kamran.movieassignment.movie_domain.use_cases

import com.kamran.movieassignment.movie_domain.repository.MovieRepository

class GetMovieDetail(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.getMovieDetail(movieId)
}