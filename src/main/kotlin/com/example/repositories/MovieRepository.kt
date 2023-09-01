package com.example.repositories

import com.example.interfaces.MovieRepository
import com.example.models.Movie
import java.util.*


class MovieRepositoryImpl(private val movieList: MutableList<Movie>): MovieRepository {

    override suspend fun getAll(): List<Movie> = movieList

    override fun getById(id: UUID): Movie? = movieList.find { movie -> movie.id == id }

    override suspend fun create(movie: Movie): Movie {
        movieList.add(movie)

        return movie
    }

    override suspend fun update(id: UUID, movie: Movie): Movie? {
        val movieRetrieved = getById(id) ?: return null
        val updatedMovie = movie.copy(id = id)

        Collections.replaceAll(movieList, movieRetrieved, updatedMovie)

        return updatedMovie
    }

    override suspend fun delete(id: UUID) {
        val movieRetrieved = getById(id)

        movieList.remove(movieRetrieved)
    }
}
