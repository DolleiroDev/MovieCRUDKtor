package com.example.transport

import com.example.models.Movie
import java.util.*

data class CreateMovieRequest(
    val movie: MovieRequest,
)

data class MovieRequest(
    val name: String,
    val categories: List<String>,
    val amount: Double,
) {
    companion object {
        fun fromMovie(movie: Movie) = MovieRequest(
            name = movie.name,
            categories = movie.categories,
            amount = movie.amount
        )
    }
    fun toMovie() = Movie(
        name = name,
        categories = categories,
        amount = amount,
    )
}

data class CreateMovieResponse(
    val movie: MovieResponse,
)

data class MovieResponse(
    val id: UUID,
    val name: String,
    val categories: List<String>,
    val amount: Double
) {
    companion object {
        fun fromMovie(movie: Movie) = MovieResponse(
            id = movie.id,
            name = movie.name,
            categories = movie.categories,
            amount = movie.amount
        )
    }
}
