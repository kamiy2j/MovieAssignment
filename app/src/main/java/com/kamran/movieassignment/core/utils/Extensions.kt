package com.kamran.movieassignment.core.utils

import com.kamran.movieassignment.movie_data.remote.dto.Genre

fun Int.toHourMinFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${if (hours > 0) "${hours}h " else ""}${minutes}min"
}

fun List<Genre>?.toGenreString(separator: String = " | "): String {
    return this?.joinToString(separator = separator) { it.name } ?: ""
}