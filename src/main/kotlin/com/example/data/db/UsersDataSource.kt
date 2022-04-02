package com.example.data.db

import com.example.domain.users.model.User
import com.example.domain.users.repositories.UsersRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*

class UsersDataSource(private val database: Database) : UsersRepository {

    override suspend fun addUser(user: User): User {
        val key = database.insertAndGenerateKey(UserTable) {
            set(it.name, user.name)
        }
        val userId = key.toString().toLong()
        return user.copy(id = userId)
    }

    override suspend fun getUsers(): List<User> {
        val query = database.from(UserTable).select()
        val users = mutableListOf<User>()
        for (row in query) {
            val id = row[UserTable.id]
            val name = row[UserTable.name]
            users.add(User(id = id!!, name = name!!))
        }
        return users
    }

    override suspend fun getUserById(id: Long): User? {
        val query = database.from(UserTable)
            .select()
            .where { (UserTable.id eq id) }

        if (query.totalRecords == 0) return null

        val row = query.iterator().next()
        val userId = row[UserTable.id]
        val name = row[UserTable.name]
        return User(id = userId!!, name = name!!)
    }

}