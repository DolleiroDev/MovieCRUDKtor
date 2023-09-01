package com.example.interfaces

import com.example.models.Movie
import java.util.*

interface MovieRepository {
    suspend fun getAll(): List<Movie>
    fun getById(id: UUID): Movie?
    suspend fun create(movie: Movie): Movie
    suspend fun update(id: UUID, movie: Movie): Movie?
    suspend fun delete(id: UUID)
}
