package com.example.repositories

import com.example.database.MovieObject
import com.example.interfaces.MovieRepository
import com.example.models.Movie
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.postgresql.ds.PGSimpleDataSource
import java.util.*


val movie = Movie(id = UUID.fromString("10cd1cfa-e4ab-4f3c-a7d0-0589b7a39562"), name = "The Truman Show")
val movieToBeCreated = Movie(id = UUID.fromString("081172c1-a392-40cd-99d0-85a07b55d620"), name = "Interstellar")
private val movieList = mutableListOf(movie)

private val movieRepository: MovieRepository = MovieRepositoryImpl()

val dataSource = PGSimpleDataSource().apply {
    user = "postgres"
    password = "postgres"
    databaseName = "database_test"
}
val database = Database.connect(dataSource)

class MovieRepositoryTest {

    @Before fun createTable() {
        transaction(database) {
            SchemaUtils.drop(MovieObject)
            SchemaUtils.create(MovieObject)
            MovieObject.insert {
                it[uuid] = movie.id
                it[name] = movie.name
            }
        }
    }

    @AfterEach fun resetDb() {
        transaction(database) {
            SchemaUtils.drop(MovieObject)
        }
    }

    @Test
    fun `should return a movie with specific id`() {
        val movieRetrieved = movieRepository.getById(movie.id)

        Assert.assertEquals(movieRetrieved, movie)
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
    fun `should create a movie in list`() = runBlocking {
        val movieCreated = movieRepository.create(movieToBeCreated)

        Assert.assertEquals(movieCreated, movieToBeCreated)
    }

    @Test
    fun `should update a movie in list`() = runBlocking{
        val movieToUpdate = Movie(name = "The Butterfly Effect 2")
        val safeMovie = movieToUpdate.copy(movie.id)
        val updatedMovie = movieRepository.update(movie.id, movieToUpdate)

        Assert.assertEquals(updatedMovie, safeMovie)
    }

    @Test
    fun `should delete a movie in list`() = runBlocking{
        movieRepository.delete(movie.id)
        val movieRetrieved = movieRepository.getById(movie.id)
        Assert.assertEquals(movieRetrieved, null)
    }
}