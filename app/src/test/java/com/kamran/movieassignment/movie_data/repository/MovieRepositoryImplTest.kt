package com.kamran.movieassignment.movie_data.repository

import com.google.common.truth.Truth.assertThat
import com.kamran.movieassignment.movie_data.remote.MovieDBApi
import com.kamran.movieassignment.movie_data.remote.invalidMovieDetailResponse
import com.kamran.movieassignment.movie_data.remote.invalidMovieListResponse
import com.kamran.movieassignment.movie_data.remote.validMovieDetailResponse
import com.kamran.movieassignment.movie_data.remote.validMovieListResponse
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.runBlocking

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class MovieRepositoryImplTest {
    private lateinit var repository: MovieRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: MovieDBApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(MovieDBApi::class.java)

        repository = MovieRepositoryImpl(
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Search movie, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validMovieListResponse)
        )

        val result = api.fetchTrendingMovies(page = 1)

        assertThat(result.results).hasSize(20)
        assertThat(result.results[0].id).isEqualTo(603692)
        assertThat(result.results[0].title).isEqualTo("John Wick: Chapter 4")
        assertThat(result.results[19].poster_path).isEqualTo("/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg")
        assertThat(result.results[19].vote_average).isEqualTo(8.4)
    }

    @Test
    fun `Search movie, http exception, returns exception`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validMovieListResponse)
        )

        try {
            val result = api.fetchTrendingMovies(page = 1)
        } catch (e: HttpException) {
            assertThat(e.code()).isEqualTo(403)
        }
    }

    @Test
    fun `Search movie, invalid response, returns exception`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(invalidMovieListResponse)
        )

        try {
            val result = api.fetchTrendingMovies(page = 1)
        } catch (e: JsonDataException) {
            assertThat(e.message).contains("Expected a boolean but was STRING at path")
        }
    }

    @Test
    fun `Search movie detail, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validMovieDetailResponse)
        )

        val result = repository.getMovieDetail(603692).onSuccess {
            assertThat(it.id).isEqualTo(603692)
            assertThat(it.title).isEqualTo("John Wick: Chapter 4")
        }

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `Search movie detail, http exception, returns exception`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validMovieDetailResponse)
        )
        try {
            val result = repository.getMovieDetail(603692)
        } catch (e: HttpException) {
            assertThat(e.code()).isEqualTo(403)
        }
    }

    @Test
    fun `Search movie detail, invalid response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(invalidMovieDetailResponse)
        )

        val result = repository.getMovieDetail(603692)

        assertThat(result.isFailure).isTrue()

    }

}