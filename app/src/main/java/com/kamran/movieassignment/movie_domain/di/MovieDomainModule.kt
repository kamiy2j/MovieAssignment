package com.kamran.movieassignment.movie_domain.di

import com.kamran.movieassignment.movie_domain.repository.MovieRepository
import com.kamran.movieassignment.movie_domain.use_cases.FetchMovies
import com.kamran.movieassignment.movie_domain.use_cases.GetMovieDetail
import com.kamran.movieassignment.movie_domain.use_cases.MovieUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MovieDomainModule {
    @ViewModelScoped
    @Provides
    fun provideMovieUseCases(repository: MovieRepository) =
        MovieUseCases(
            fetchMovies = FetchMovies(repository),
            getMovieDetail = GetMovieDetail(repository)
        )
}