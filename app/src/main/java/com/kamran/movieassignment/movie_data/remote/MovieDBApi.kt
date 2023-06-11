package com.kamran.movieassignment.movie_data.remote

import com.kamran.movieassignment.movie_data.remote.dto.MovieDetailDto
import com.kamran.movieassignment.movie_data.remote.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {

    @GET("discover/movie")
    suspend fun fetchTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): MovieListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): MovieDetailDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3"
    }
}