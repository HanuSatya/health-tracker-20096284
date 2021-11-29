package org.wit.utilities

import org.jetbrains.exposed.sql.ResultRow
import org.wit.db.Users
import org.wit.domain.UserDTO
import org.wit.db.Activities
import org.wit.domain.ActivityDTO

fun mapToUserDTO(it: ResultRow) = UserDTO(
    id = it[Users.id],
    full_name = it[Users.full_name],
    phone_number = it[Users.phone_number],
    email_id = it[Users.email_id],
    age = it[Users.age],
    gender = it[Users.gender],
    address = it[Users.address]
)

fun mapToActivityDTO(it: ResultRow) = ActivityDTO(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)