package org.wit.utilities

import org.jetbrains.exposed.sql.ResultRow
import org.wit.db.Users
import org.wit.domain.UserDTO

fun mapToUserDTO(it: ResultRow) = UserDTO(
    id = it[Users.id],
    full_name = it[Users.full_name],
    phone_number = it[Users.phone_number],
    email_id = it[Users.email_id],
    age = it[Users.age],
    gender = it[Users.gender],
    address = it[Users.address]
)