package com.example.services

import MovieCreatedProducer
import com.example.interfaces.MovieRepository
import com.example.models.Movie
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*


private val repository: MovieRepository = mockk()
private val movieCreatedProducer: MovieCreatedProducer = mockk()
private val service = MovieService(repository, movieCreatedProducer)

private val movie = Movie(
    id = UUID.fromString("cff82b8b-b05c-4c00-866a-cc7f79cc972e"),
    name = "Scream"

)

private val movieList = mutableListOf(
    Movie(id = UUID.fromString("9709ab56-269f-47bd-a46b-6f0d045dab23"), name = "The Butterfly Effect")
)

class MovieServiceTest {

    @Test
    fun `should return a list with movies` () = runBlocking {
        coEvery { repository.getAll() } returns movieList

        val movieListRetrieved = service.getAll()

        Assert.assertEquals(movieListRetrieved, movieList)

        coVerify(exactly = 1){repository.getAll() }
    }

    @Test
    fun `should return a specific movie`() {
        every { repository.getById(movie.id) } returns movie

        val movieRetrieved = service.getById(movie.id)

        Assert.assertEquals(movieRetrieved, movie)

        verify(exactly = 1) { repository.getById(movie.id) }

    }

    @Test
    fun `should create a movie on list`() = runBlocking {
        coEvery { repository.create(movie) } returns movie
        coEvery { movieCreatedProducer.sendMovie(movie) } returns Unit

        val movieCreated = service.create(movie)

        Assert.assertEquals(movieCreated, movie)

        coVerify (exactly = 1) { repository.create(movie) }
    }

    @Test
    fun `should update a movie on list`() = runBlocking {
        coEvery { repository.update(movie.id, movie) } returns movie

        val movieUpdated = service.update(movie.id, movie)

        Assert.assertEquals(movieUpdated, movie)

        coVerify (exactly = 1) { repository.update(movie.id, movie) }
    }

    @Test
    fun `should delete a movie on list`() = runBlocking {
        coEvery { repository.delete(movie.id) } returns Unit

        service.delete(movie.id)

        coVerify (exactly = 1) { repository.delete(movie.id) }
    }
}