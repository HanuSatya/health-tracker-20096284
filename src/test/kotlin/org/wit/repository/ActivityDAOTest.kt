package org.wit.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.wit.db.Activities
import org.wit.domain.ActivityDTO
import org.wit.helpers.activities
import org.wit.helpers.populateActivityTable
import org.wit.helpers.populateUserTable
import kotlin.test.assertEquals

//retrieving some test data from Fixtures
private val activity1 = activities.get(0)
private val activity2 = activities.get(1)
private val activity3 = activities.get(2)
private val activity4 = activities.get(3)
private val activity5 = activities.get(4)
private val activity6 = activities.get(5)

class ActivityDAOTest {

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

//    @Nested
//    inner class CreateActivities {
//
//        @Test
//        fun `multiple activities added to table can be retrieved successfully`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//                assertEquals(activity1, activityDAO.findByActivityId(activity1.id))
//                assertEquals(activity2, activityDAO.findByActivityId(activity2.id))
//                assertEquals(activity3, activityDAO.findByActivityId(activity3.id))
//                assertEquals(activity4, activityDAO.findByActivityId(activity4.id))
//                assertEquals(activity5, activityDAO.findByActivityId(activity5.id))
//                assertEquals(activity6, activityDAO.findByActivityId(activity6.id))
//            }
//        }
//    }
//
//    @Nested
//    inner class ReadActivities {
//
//        @Test
//        fun `getting all activities from a populated table returns all rows`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//            }
//        }
//
//        @Test
//        fun `get activity by user id that has no activities, results in no record returned`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(0, activityDAO.findByUserId(6).size)
//            }
//        }
//
//        @Test
//        fun `get activity by user id that exists, results in a correct activitie(s) returned`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(activity1, activityDAO.findByUserId(1).get(0))
//                assertEquals(activity2, activityDAO.findByUserId(2).get(0))
//                assertEquals(activity3, activityDAO.findByUserId(3).get(0))
//                assertEquals(activity4, activityDAO.findByUserId(1).get(1))
//                assertEquals(activity5, activityDAO.findByUserId(2).get(1))
//                assertEquals(activity6, activityDAO.findByUserId(4).get(0))
//            }
//        }
//
//        @Test
//        fun `get all activities over empty table returns none`() {
//            transaction {
//
//                //Arrange - create and setup activityDAO object
//                SchemaUtils.create(Activities)
//                val activityDAO = ActivityDAO()
//
//                //Act & Assert
//                assertEquals(0, activityDAO.getAll().size)
//            }
//        }
//
//        @Test
//        fun `get activity by activity id that has no records, results in no record returned`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//                //Act & Assert
//                assertEquals(null, activityDAO.findByActivityId(7))
//            }
//        }
//
//        @Test
//        fun `get activity by activity id that exists, results in a correct activity returned`() {
//            transaction {
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//                //Act & Assert
//                assertEquals(activity1, activityDAO.findByActivityId(1))
//                assertEquals(activity2, activityDAO.findByActivityId(2))
//                assertEquals(activity3, activityDAO.findByActivityId(3))
//                assertEquals(activity6, activityDAO.findByActivityId(6))
//
//            }
//        }
//    }
//
//    @Nested
//    inner class UpdateActivities {
//
//        @Test
//        fun `updating existing activity in table results in successful update`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                val activity6updated = ActivityDTO(id = 6, description = "Cardio", duration = 42.0,
//                    calories = 220, started = DateTime.now(), userId = 4)
//                activityDAO.updateByActivityId(activity6updated.id, activity6updated)
//                assertEquals(activity6updated, activityDAO.findByActivityId(6))
//            }
//        }
//
//        @Test
//        fun `updating non-existant activity in table results in no updates`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                val activity7updated = ActivityDTO(id = 7, description = "Cardio", duration = 42.0,
//                    calories = 220, started = DateTime.now(), userId = 4)
//                activityDAO.updateByActivityId(7, activity7updated)
//                assertEquals(null, activityDAO.findByActivityId(7))
//                assertEquals(6, activityDAO.getAll().size)
//            }
//        }
//    }
//
//    @Nested
//    inner class DeleteActivities {
//
//        @Test
//        fun `deleting a non-existant activity (by id) in table results in no deletion`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//                activityDAO.deleteByActivityId(7)
//                assertEquals(6, activityDAO.getAll().size)
//            }
//        }
//
//        @Test
//        fun `deleting an existing activity (by id) in table results in record being deleted`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//                activityDAO.deleteByActivityId(activity6.id)
//                assertEquals(5, activityDAO.getAll().size)
//            }
//        }
//
//
//        @Test
//        fun `deleting activities when none exist for user id results in no deletion`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//                activityDAO.deleteByUserId(6)
//                assertEquals(6, activityDAO.getAll().size)
//            }
//        }
//
//        @Test
//        fun `deleting activities when 1 or more exist for user id results in deletion`() {
//            transaction {
//
//                //Arrange - create and populate tables with six users and six activities
//                val userDAO = populateUserTable()
//                val activityDAO = populateActivityTable()
//
//                //Act & Assert
//                assertEquals(6, activityDAO.getAll().size)
//                activityDAO.deleteByUserId(1)
//                assertEquals(4, activityDAO.getAll().size)
//            }
//        }
//    }
}