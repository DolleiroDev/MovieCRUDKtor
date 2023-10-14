package com.example.models

import java.util.*

data class Movie(
    val id: UUID = UUID.randomUUID(),
    val name: String,
)
