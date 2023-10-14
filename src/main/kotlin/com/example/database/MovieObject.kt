package com.example.database

import org.jetbrains.exposed.sql.Table

object MovieObject : Table() {
    val uuid = uuid("id")
    val name = varchar("name", 50)
    override val primaryKey = PrimaryKey(uuid, name = "movie_uuid")
}