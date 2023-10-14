package com.example.IoC

import MovieCreatedProducer
import com.example.consumers.StockConsumer
import com.example.controllers.MovieController
import com.example.interfaces.MovieRepository
import com.example.interfaces.StockRepository
import com.example.models.Movie
import com.example.repositories.MovieRepositoryImpl
import com.example.repositories.StockRepositoryImpl
import com.example.services.MovieService
import com.example.services.StockService
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModules = module {
    single { MovieController(get()) }
    single{ MovieService(get(), get())}
    single{ StockService(get())}
    single<MovieRepository> { MovieRepositoryImpl()}
    single<StockRepository> { StockRepositoryImpl(get(named("stockList")))}
    single(named("stockList")){
        mutableListOf<Movie>()
    }
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    single{ MovieCreatedProducer()}
    single { StockConsumer(get(), get(named("IODispatcher"))) }
}
