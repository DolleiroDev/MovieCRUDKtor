package com.example.repositories

import com.example.interfaces.MovieRepository
import com.example.models.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*


private val movieList = mutableListOf(
    Movie(id = UUID.fromString("9709ab56-269f-47bd-a46b-6f0d045dab23"), name = "The Butterfly Effect",
        categories = listOf("Drama"), amount = 35.25)
    )
val movie = Movie(id = UUID.fromString("10cd1cfa-e4ab-4f3c-a7d0-0589b7a39562"), name = "The Truman Show",
    categories = listOf("Sci-fi", "Drama"), amount = 51.21)

private val movieRepository: MovieRepository = MovieRepositoryImpl(movieList)

val correctIdParsed: UUID = UUID.fromString("9709ab56-269f-47bd-a46b-6f0d045dab23")

class MovieRepositoryTest {

    @Test
    fun `should return a movie with specific id`() {
        val movieRetrieved = movieRepository.getById(correctIdParsed)

        Assert.assertEquals(movieRetrieved, movieList.first())
    }

    @Test
    fun `should return a list with movies`() = runBlocking {
        val movies = movieRepository.getAll()

        Assert.assertEquals(movies, movieList)
    }

    @Test
    fun `should return null when use get with a id who doesn't exists`() = runBlocking {
        val wrongIdParsed = UUID.fromString("17bb5776-58d9-483d-8264-3dc4a00164f5")

        val movieRetrieved = movieRepository.getById(wrongIdParsed)

        Assert.assertNull(movieRetrieved)
    }

    @Test
    fun `should create a movie in list`() = runBlocking{
        val movieCreated = movieRepository.create(movie)

        Assert.assertEquals(movieCreated, movie)
        Assert.assertTrue(movieList.contains(movieCreated))
    }

    @Test
    fun `should update a movie in list`() = runBlocking{
        val movieToUpdate = Movie(name = "The Butterfly Effect 2",
            categories = listOf("Drama", "Filme Ruim"), amount = 10.0)
        val safeMovie = movieToUpdate.copy(correctIdParsed)
        val updatedMovie = movieRepository.update(correctIdParsed, movieToUpdate)

        Assert.assertEquals(updatedMovie, safeMovie)
    }

    @Test
    fun `should delete a movie in list`() = runBlocking{
        val firstMovie = movieList.first()

        movieRepository.delete(firstMovie.id)

        Assert.assertFalse(movieList.contains(firstMovie))
    }
}