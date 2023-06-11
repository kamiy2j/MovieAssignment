package com.kamran.movieassignment.core.utils

import com.google.common.truth.Truth.assertThat
import com.kamran.movieassignment.movie_data.remote.dto.Genre
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `Min to Hour Min Format`() {
        val mins = 90
        val expectedValue = "1h 30min"
        val actualValue = mins.toHourMinFormat()

        assertThat(actualValue).isEqualTo(expectedValue)
    }

    @Test
    fun `genre list to string`() {
        val genreList = listOf(
            Genre(1, "Action"),
            Genre(2, "Drama"),
            Genre(3, "Comedy")
        )
        val expectedValue = "Action | Drama | Comedy"
        val actualValue = genreList.toGenreString()

        assertThat(actualValue).isEqualTo(expectedValue)
    }

}