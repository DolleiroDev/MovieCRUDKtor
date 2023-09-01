package com.example.services

import com.example.interfaces.StockRepository
import com.example.models.Movie

class StockService(private val stockRepository: StockRepository) {
    suspend fun getAll(): List<Movie> = stockRepository.getAll()

    suspend fun create(movie: Movie): Movie = stockRepository.create(movie)
}