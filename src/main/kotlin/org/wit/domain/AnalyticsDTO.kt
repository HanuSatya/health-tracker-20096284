package org.wit.domain

import org.joda.time.DateTime

data class AnalyticsDTO(
    var height: Int,
    var weight: Int,
    var user_id: Int,
    var avg_sleep: Int,
    var calorie_intake: Int,
    var heart_rate: Int,
    var healthy: Boolean,
    var description: String,
    var created_at: DateTime?
    )

/*
    val height = integer("height")
    val weight = integer("weight")
    val user_id = integer("user_id")
    val avg_sleep = integer("avg_sleep")
    val calorie_intake = integer("calorie_intake")
    val heart_rate = integer("heart_rate")
    val healthy = integer("healthy")
    var description = varchar("description", 100)
 */
