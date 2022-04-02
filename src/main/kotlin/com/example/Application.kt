package com.example

import com.example.api.users.configureUsersRouting
import com.example.data.db.UsersDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*

fun main() {
    val usersDataSource = UsersDataSource()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureUsersRouting(usersRepository = usersDataSource)
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }
}
