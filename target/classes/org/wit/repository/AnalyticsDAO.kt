package org.wit.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.wit.db.Analytics
import org.wit.db.Users
import org.wit.domain.AnalyticsDTO
import org.wit.domain.UserAnalyticsDTO
import org.wit.utilities.mapToActivityDTO
import org.wit.utilities.mapToAnalyticsDTO
import org.wit.utilities.mapToAnalyticsDTO
import org.wit.utilities.maptoUserAnalyticsDTO


class AnalyticsDAO {

    fun getAll(): ArrayList<AnalyticsDTO> {
        val list: ArrayList<AnalyticsDTO> = arrayListOf()
        transaction {
            Analytics.selectAll().map {
                list.add(mapToAnalyticsDTO(it))
            }
        }
        return list
    }

    fun findByUserId(id: Int): AnalyticsDTO? {
        return transaction {
            Analytics.select() {
                Analytics.user_id eq id
            }
            .map { mapToAnalyticsDTO(it) }
            .firstOrNull()
        }
    }

    fun getTable(): ArrayList<AnalyticsDTO> {
        val list: ArrayList<AnalyticsDTO> = arrayListOf()
        transaction {
            //select DISTINCT ON (user_id) * from (select * from analytics ORDER BY created_at DESC) as ordered
            Analytics.selectAll().orderBy(Analytics.created_at to SortOrder.DESC).map {
                list.add(mapToAnalyticsDTO(it))
            }
        }
        return list
    }

    fun getAnalytics(): ArrayList<UserAnalyticsDTO> {
        val list: ArrayList<UserAnalyticsDTO> = arrayListOf()
        transaction {
            //SELECT * FROM users LEFT JOIN analytics ON users.id = analytics.user_id
            Users.leftJoin(Analytics, { id }, { user_id })
                .selectAll()
                .map {
                    list.add(maptoUserAnalyticsDTO(it))
                }
        }
        return list
    }

    fun save(AnalyticsDTO: AnalyticsDTO)
    {
        return transaction {
            Analytics.insert {
                it[user_id] = AnalyticsDTO.user_id
                it[height] = AnalyticsDTO.height
                it[weight] = AnalyticsDTO.weight
                it[healthy] = AnalyticsDTO.healthy
                it[avg_sleep] = AnalyticsDTO.avg_sleep
                it[calorie_intake] = AnalyticsDTO.calorie_intake
                it[description] = AnalyticsDTO.description
                it[heart_rate] = AnalyticsDTO.heart_rate
            }
        }
    }

    fun update(user_id: Int, AnalyticsDTO: AnalyticsDTO): Int {
        return transaction {
            Analytics.update({
                Analytics.user_id eq user_id })
            {
                it[height] = AnalyticsDTO.height
                it[weight] = AnalyticsDTO.weight
                it[healthy] = AnalyticsDTO.healthy
                it[avg_sleep] = AnalyticsDTO.avg_sleep
                it[calorie_intake] = AnalyticsDTO.calorie_intake
                it[description] = AnalyticsDTO.description
                it[heart_rate] = AnalyticsDTO.heart_rate
            }
        }
    }

    fun delete(user_id: Int): Int {
        return transaction {
            Analytics.deleteWhere { Analytics.user_id eq user_id }
        }
    }

}
