package com.kamran.movieassignment.movie_data.remote.dto

data class MovieDetailDto(
    val adult: Boolean? = false,
    val backdrop_path: String? = "",
    val belongs_to_collection: BelongsToCollection,
    val budget: Int? = 0,
    val genres: List<Genre>? = emptyList(),
    val homepage: String? = "",
    val id: Int? = 0,
    val imdb_id: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val overview: String? = "",
    val popularity: Double,
    val poster_path: String? = "",
    val production_companies: List<ProductionCompany>? = emptyList(),
    val production_countries: List<ProductionCountry>? = emptyList(),
    val release_date: String? = "",
    val revenue: Long,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>? = emptyList(),
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0
)