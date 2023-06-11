package com.kamran.movieassignment.movie_data.di

import com.kamran.movieassignment.movie_data.remote.MovieDBApi
import com.kamran.movieassignment.movie_data.repository.MovieRepositoryImpl
import com.kamran.movieassignment.movie_domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDBApi(
        okHttpClient: OkHttpClient
    ): MovieDBApi {
        return Retrofit.Builder()
            .baseUrl(MovieDBApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieDBApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieDBApi
    ): MovieRepository {
        return MovieRepositoryImpl(
            api = api
        )
    }

}