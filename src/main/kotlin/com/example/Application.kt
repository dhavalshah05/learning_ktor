package com.example

import com.example.api.users.usersRouting
import com.example.data.db.UsersDataSource
import com.google.gson.Gson
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*

fun main() {
    val gson = Gson()
    val filePath = "./data/users.text"
    val usersDataSource = UsersDataSource(gson, filePath)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        routing {
            route("/api") {
                usersRouting(usersRepository = usersDataSource)
            }
        }
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }
}
