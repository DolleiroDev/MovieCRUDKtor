package com.example.controllers

import com.example.models.Movie
import com.example.services.MovieService
import com.example.transport.CreateMovieRequest
import com.example.transport.MovieResponse
import java.util.*

class MovieController(private val movieService: MovieService) {

    suspend fun getAll(): List<Movie> = movieService.getAll()

     fun getById(id: UUID): MovieResponse? {
        val movieSearched = movieService.getById(id) ?: return null

        return MovieResponse.fromMovie(movieSearched)
    }

    suspend fun create(movieRequest: CreateMovieRequest): MovieResponse {
        val movie = movieRequest.movie.toMovie()
        val movieCreated = movieService.create(movie)

        return MovieResponse.fromMovie(movieCreated)
    }

    suspend fun update(id: UUID, movieRequest: CreateMovieRequest): MovieResponse? {
        val movie = movieRequest.movie.toMovie()
        val movieUpdated = movieService.update(id, movie) ?: return null

        return MovieResponse.fromMovie(movieUpdated)
    }

    suspend fun delete(id: UUID) = movieService.delete(id)
}
