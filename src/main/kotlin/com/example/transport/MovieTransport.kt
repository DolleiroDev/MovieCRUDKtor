package com.example.transport

import com.example.models.Movie
import java.util.*

data class CreateMovieRequest(
    val movie: MovieRequest,
)

data class MovieRequest(
    val name: String,
) {
    companion object {
        fun fromMovie(movie: Movie) = MovieRequest(
            name = movie.name
        )
    }
    fun toMovie() = Movie(
        name = name
    )
}

data class CreateMovieResponse(
    val movie: MovieResponse
)

data class MovieResponse(
    val id: UUID,
    val name: String,

) {
    companion object {
        fun fromMovie(movie: Movie) = MovieResponse(
            id = movie.id,
            name = movie.name,

        )
    }
}
