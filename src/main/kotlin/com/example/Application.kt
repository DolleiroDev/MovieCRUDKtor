package com.example

import com.example.IoC.serviceModules
import com.example.consumers.StockConsumer
import com.example.routes.apiRoutes
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.ktor.ext.inject


fun main(args: Array<String>) {
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
