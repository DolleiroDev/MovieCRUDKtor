package com.example.repositories

import com.example.interfaces.StockRepository
import com.example.models.Movie

class StockRepositoryImpl(private val stockList: MutableList<Movie>): StockRepository{
    override suspend fun getAll(): List<Movie> = stockList

    override suspend fun create(movie: Movie): Movie {
        stockList.add(movie)

        return movie
    }
}