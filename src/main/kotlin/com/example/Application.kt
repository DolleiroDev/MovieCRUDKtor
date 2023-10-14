package com.example

import com.example.IoC.serviceModules
import com.example.consumers.StockConsumer
import com.example.database.MovieObject
import com.example.routes.apiRoutes
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.startKoin
import org.koin.ktor.ext.inject
import org.postgresql.ds.PGSimpleDataSource


fun main(args: Array<String>) {

    val dataSource = PGSimpleDataSource().apply {
        user = "postgres"
        password = "postgres"
        databaseName = "postgres"
    }
    Database.connect(dataSource)
    transaction {
        SchemaUtils.create(MovieObject)
    }
    startKoin {
        modules(serviceModules)
    }

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val stockConsumer: StockConsumer by inject()
    stockConsumer.start()

    routing {
        apiRoutes()
    }

    install(ContentNegotiation) {
        gson {

        }
    }
}
