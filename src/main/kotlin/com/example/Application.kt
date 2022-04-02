package com.example

import com.example.api.users.configureUsersRouting
import com.example.data.db.UsersDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import org.ktorm.database.Database

fun main() {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/testdb",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "root"
    )

    val usersDataSource = UsersDataSource(database)

    embeddedServer(Netty, port = 3000, host = "0.0.0.0") {
        configureUsersRouting(usersRepository = usersDataSource)
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }
}
