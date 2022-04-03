package com.example.api.users

import com.example.api.commons.ApiResponse
import com.example.domain.users.model.User
import com.example.domain.users.repositories.UsersRepository
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*

fun Route.usersRouting(
    usersRepository: UsersRepository
) {
    route("/users") {
        post {
            val userRequest = call.receive<UserRequest>()

            val user = User(id = 0L, name = userRequest.name)
            val addedUser = usersRepository.addUser(user)

            val response = ApiResponse(data = addedUser, message = "User added")
            call.respond(HttpStatusCode.OK, response)
        }

        get {
            val users = usersRepository.getUsers()
            val message = if (users.isEmpty()) {
                "Users not found"
            } else {
                "Users found"
            }
            val response = ApiResponse(message = message, data = users)
            call.respond(HttpStatusCode.OK, response)
        }

        get("/{userId}") {
            val userId = call.parameters["userId"]?.toLongOrNull()
            if (userId == null) {
                val response = ApiResponse.withMessage("User not found for given id")
                call.respond(HttpStatusCode.NotFound, response)
                return@get
            }

            val user = usersRepository.getUserById(userId)
            if (user == null) {
                val response = ApiResponse.withMessage("User not found for given id")
                call.respond(HttpStatusCode.NotFound, response)
                return@get
            }

            val response = ApiResponse(data = user, message = "User found")
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
