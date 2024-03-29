package org.wit.utilities

import org.jetbrains.exposed.sql.ResultRow
import org.wit.db.Users
import org.wit.domain.UserDTO
import org.wit.db.Activities
import org.wit.db.Analytics
import org.wit.domain.ActivityDTO
import org.wit.domain.AnalyticsDTO
import org.wit.domain.UserAnalyticsDTO

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

fun mapToAnalyticsDTO(it: ResultRow) = AnalyticsDTO(
    user_id = it[Analytics.user_id],
    description = it[Analytics.description],
    healthy = it[Analytics.healthy],
    weight = it[Analytics.weight],
    height = it[Analytics.height],
    calorie_intake = it[Analytics.calorie_intake],
    heart_rate = it[Analytics.heart_rate],
    avg_sleep = it[Analytics.avg_sleep],
    created_at = it[Analytics.created_at]
)

fun maptoUserAnalyticsDTO(it: ResultRow) = UserAnalyticsDTO(
    user_id = it[Analytics.user_id],
    description = it[Analytics.description],
    healthy = it[Analytics.healthy],
    weight = it[Analytics.weight],
    height = it[Analytics.height],
    calorie_intake = it[Analytics.calorie_intake],
    heart_rate = it[Analytics.heart_rate],
    avg_sleep = it[Analytics.avg_sleep],
    created_at = it[Analytics.created_at],
    id = it[Users.id],
    full_name = it[Users.full_name],
    phone_number = it[Users.phone_number],
    email_id = it[Users.email_id],
    age = it[Users.age],
    gender = it[Users.gender],
    address = it[Users.address]
)