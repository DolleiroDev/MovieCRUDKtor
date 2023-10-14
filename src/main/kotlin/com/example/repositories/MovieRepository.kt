package com.example.repositories

import com.example.database.MovieObject
import com.example.interfaces.MovieRepository
import com.example.models.Movie
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*



class MovieRepositoryImpl: MovieRepository {

    override suspend fun getAll(): List<Movie> {
        return transaction {
            MovieObject.selectAll().map {
                Movie(it[MovieObject.uuid], it[MovieObject.name])
            }
        }
    }

    override fun getById(id: UUID): Movie? {
        val movieRetrieved = transaction {
            MovieObject.select(MovieObject.uuid eq id).firstOrNull()
        } ?: return null

        return Movie(movieRetrieved[MovieObject.uuid], movieRetrieved[MovieObject.name])
    }

    override suspend fun create(movie: Movie): Movie {
        transaction {
            MovieObject.insert {
                it[uuid] = movie.id
                it[name] = movie.name
            }
        }

        return movie
    }

    override suspend fun update(id: UUID, movie: Movie): Movie? {
        transaction {
            MovieObject.select(MovieObject.uuid eq id).firstOrNull()
        } ?: return null

        transaction {
            MovieObject.update ({ MovieObject.uuid eq id }) {
                it[name] = movie.name
            }
        }

        return Movie(id = id, name = movie.name)
    }

    override suspend fun delete(id: UUID) {
        transaction {
            MovieObject.deleteWhere{MovieObject.uuid eq id}
        }
    }

}
