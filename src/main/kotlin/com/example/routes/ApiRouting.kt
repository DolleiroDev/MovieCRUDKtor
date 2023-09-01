package com.example.routes

import com.example.controllers.MovieController
import com.example.interfaces.StockRepository
import com.example.repositories.StockRepositoryImpl
import com.example.transport.CreateMovieRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.apiRoutes() {

    val movieController: MovieController by inject()
    val stockRepository: StockRepository by inject()

    route("/movies") {
        get {
            val moviesReturned = movieController.getAll()

            call.respond(moviesReturned)
        }

        get("stock/all") {
            call.respond(stockRepository.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val idParsed = UUID.fromString(id)
            val movieHandled = movieController.getById(idParsed)

            if (movieHandled == null) {
                call.respond(HttpStatusCode.BadRequest)
            }

            call.respond(movieHandled!!)
        }

        post {
            val movie = call.receive<CreateMovieRequest>()
            val movieCreated = movieController.create(movie)

            call.respond(HttpStatusCode.Created, movieCreated)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val idParsed = UUID.fromString(id)
            val movie = call.receive<CreateMovieRequest>()
            val updatedMovie = movieController.update(idParsed, movie) ?: return@put call.respond(HttpStatusCode.NotFound)

            call.respond(updatedMovie)
        }

        delete("/{id}"){
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val idParsed = UUID.fromString(id)

            movieController.delete(idParsed)

            call.respond(HttpStatusCode.OK)
        }
    }
}
