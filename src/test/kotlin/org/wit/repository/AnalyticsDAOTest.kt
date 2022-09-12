package org.wit.repository

import kotlin.test.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.wit.db.Users
import org.wit.domain.AnalyticsDTO
import org.wit.domain.UserDTO
import org.wit.helpers.*
import kotlin.test.assertNotEquals


var userId = 10

class AnalyticsDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect(
                "jdbc:postgresql://ec2-54-221-74-111.compute-1.amazonaws.com:5432/d45ep7ga1tbhhk?sslmode=require",
                driver = "org.postgresql.Driver",
                user = "mksllimlbcetcr",
                password = "05b76274aa7f4940997675f77f1862064be5ef82d991fa76c2f3f971dd107db3")
        }
    }

    @Nested
    inner class AddAnalytics {

        @Test
        fun `Add Analytics to user`() {
            transaction {
                var analyticsDAO = AnalyticsDAO()
                var count = analyticsDAO.getAll().size
                analyticsDAO.save(AnalyticsDTO(150, 80, userId, 8, 2000, 75, true, "You're doing great!", DateTime()))
                assertEquals(count+1, analyticsDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class ReadUsers {

        @Test
        fun `getting all Analytics`() {
            transaction {
                var analyticsDAO = AnalyticsDAO()
                assertNotEquals(0, analyticsDAO.getAll().size)
            }
        }

        @Test
        fun `get all Analytics table`() {
            transaction {
                var analyticsDAO = AnalyticsDAO()
                assertNotEquals(0, analyticsDAO.getTable().size)
            }
        }

    }

}