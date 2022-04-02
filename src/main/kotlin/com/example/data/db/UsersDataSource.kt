package com.example.data.db

import com.example.domain.users.model.User
import com.example.domain.users.repositories.UsersRepository
import java.util.concurrent.atomic.AtomicLong

class UsersDataSource : UsersRepository {

    private val idCounter = AtomicLong(0)
    private val users = mutableListOf<User>()

    override suspend fun addUser(user: User): User {
        val userToInsert = user.copy(id = idCounter.incrementAndGet())
        users.add(userToInsert)
        return userToInsert
    }

    override suspend fun getUsers(): List<User> {
        return users
    }

    override suspend fun getUserById(id: Long): User? {
        return users.find { it.id == id }
    }

}