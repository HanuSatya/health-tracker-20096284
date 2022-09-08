package org.wit.db

import org.jetbrains.exposed.sql.Table

object Analytics : Table("analytics")
{
    val height = integer("height")
    val weight = integer("weight")
    val user_id = integer("user_id")
    val avg_sleep = integer("avg_sleep")
    val calorie_intake = integer("calorie_intake")
    val heart_rate = integer("heart_rate")
    val healthy = bool("healthy")
    val description = varchar("description", 100)
    val created_at = datetime("created_at")
}