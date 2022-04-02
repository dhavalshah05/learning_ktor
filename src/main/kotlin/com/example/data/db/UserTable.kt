package com.example.data.db

import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object UserTable : Table<Nothing>("users") {
    val id = long("id").primaryKey()
    val name = varchar("name")
}