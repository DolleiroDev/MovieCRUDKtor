package com.example.interfaces

import com.example.models.Movie

interface StockRepository {
    suspend fun getAll(): List<Movie>
    suspend fun create(movie: Movie): Movie
}