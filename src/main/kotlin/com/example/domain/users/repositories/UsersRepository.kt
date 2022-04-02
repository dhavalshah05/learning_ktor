package com.example.domain.users.repositories

import com.example.domain.users.model.User

interface UsersRepository {
    suspend fun addUser(user: User): User

    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Long): User?
}