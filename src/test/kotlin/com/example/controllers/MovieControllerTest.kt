package com.example.controllers

import com.example.models.Movie
import com.example.services.MovieService
import com.example.transport.CreateMovieRequest
import com.example.transport.MovieRequest
import com.example.transport.MovieResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*

private val movieList = mutableListOf(
    Movie(id = UUID.fromString("9709ab56-269f-47bd-a46b-6f0d045dab23"), name = "The Butterfly Effect")
)
private val service: MovieService = mockk()
private val controller: MovieController = MovieController(service)
private val movie = Movie(id = UUID.fromString("10cd1cfa-e4ab-4f3c-a7d0-0589b7a39562"), name = "The Truman Show")
private val movieRequest: MovieRequest = MovieRequest.fromMovie(movie)
private val createMovieRequest: CreateMovieRequest = CreateMovieRequest(movieRequest)
private val movieResponse: MovieResponse = MovieResponse.fromMovie(movie)

class MovieControllerTest {

    @Test
    fun `should return a list with movies` () = runBlocking {
        coEvery { service.getAll() } returns movieList

        val movieListRetrieved = controller.getAll()

        Assert.assertEquals(movieList, movieListRetrieved)

        coVerify(exactly = 1){ service.getAll() }
    }

    @Test
    fun `should get a movie by specific id` () = runBlocking {
        coEvery { service.getById(movie.id) } returns movie

        val movieCreatedResponse = controller.getById(movie.id)

        Assert.assertEquals(movieResponse, movieCreatedResponse)
        coVerify(exactly = 1){ service.getById(movie.id) }
    }

//    @Test
//    fun `should create a movie` () = runBlocking {
//        coEvery { service.create(
//            match {
//                it.id == movie.id
//                        && it.categories == movie.categories
//                        && it.name == movie.name
//            }) } returns movie
//
//        val movieCreatedResponse = controller.create(createMovieRequest)
//
//        Assert.assertEquals(movieResponse, movieCreatedResponse)
//
//        coVerify(exactly = 1){ service.create(movie) }
//    }

    @Test
    fun `should update a movie` () = runBlocking {
        coEvery { service.update(movie.id, movie) } returns movie

        val movieRequestUpdated = CreateMovieRequest(MovieRequest.fromMovie(movie))
        val movieCreatedResponse = controller.update(movie.id, movieRequestUpdated)!!

        Assert.assertEquals(movieCreatedResponse.id, movie.id)

        coVerify(exactly = 1){ service.update(movie.id, movie) }
    }

    @Test
    fun `should delete a movie` () = runBlocking {
        coEvery { service.delete(movie.id) } returns Unit

        controller.delete(movie.id)

        coVerify(exactly = 1){ service.delete(movie.id) }
    }
}