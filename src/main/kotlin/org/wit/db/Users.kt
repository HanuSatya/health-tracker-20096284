package org.wit.db


import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Users : Table("users")
{
    val id = integer("id").autoIncrement().primaryKey()
    val full_name = varchar("full_name", 100)
    val phone_number = varchar("phone_number", 10)
    val email_id = varchar("email_id", 100)
    val age = integer("age")
    val gender = varchar("gender", 10)
    val address = varchar("address", 100)
}