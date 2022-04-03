package com.example.data.db

import com.example.domain.users.model.User
import com.example.domain.users.repositories.UsersRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.concurrent.atomic.AtomicLong

class UsersDataSource(
    private val gson: Gson,
    private val filePath: String
) : UsersRepository {

    private val idCounter: AtomicLong
    private val users = mutableListOf<User>()

    init {
        val text = File(filePath).readText()
        if (text.isNotBlank()) {
            val typeToken = object : TypeToken<List<User>>(){}.type
            val loadedUsers: List<User> = gson.fromJson(text, typeToken)
            users.clear()
            users.addAll(loadedUsers)

            idCounter = AtomicLong(users.last().id)
        } else {
            idCounter = AtomicLong(0)
        }
    }

    override suspend fun addUser(user: User): User {
        return withContext(Dispatchers.IO) {
            val userToInsert = user.copy(id = idCounter.incrementAndGet())
            users.add(userToInsert)

            try {
                PrintWriter(FileWriter(filePath)).use {
                    val jsonData = gson.toJson(users)
                    it.write(jsonData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            userToInsert
        }
    }

    override suspend fun getUsers(): List<User> {
        return users
    }

    override suspend fun getUserById(id: Long): User? {
        return users.find { it.id == id }
    }

}