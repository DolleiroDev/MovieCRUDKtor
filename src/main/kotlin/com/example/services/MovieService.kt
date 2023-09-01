package com.example.services

import MovieCreatedProducer
import com.example.interfaces.MovieRepository
import com.example.models.Movie
import java.util.*

class MovieService(
    private val movieRepository: MovieRepository,
    private val movieCreatedProducer: MovieCreatedProducer
) {

    suspend fun getAll() = movieRepository.getAll()

    fun getById(id: UUID): Movie? = movieRepository.getById(id)

    suspend fun create(movie: Movie): Movie {
        movieRepository.create(movie)
        movieCreatedProducer.sendMovie(movie)

        return movie
    }

    suspend fun update(id: UUID, movie: Movie): Movie? = movieRepository.update(id, movie)

    suspend fun delete(id: UUID) = movieRepository.delete(id)
}
