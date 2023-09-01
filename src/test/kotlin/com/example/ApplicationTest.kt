package com.example

import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        client.get("/movies") {

        }
    }
}
