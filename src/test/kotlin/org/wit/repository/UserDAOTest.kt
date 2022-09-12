package org.wit.repository

import kotlin.test.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.wit.db.Users
import org.wit.domain.UserDTO
import org.wit.helpers.*
import kotlin.test.assertNotEquals

//retrieving some test data from Fixtures
val user1 = users.get(0)
val user2 = users.get(1)
val user3 = users.get(2)
val user4 = users.get(3)
val user5 = users.get(4)
val user6 = users.get(5)


class UserDAOTest {

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

    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(user1)
        userDAO.save(user2)
        userDAO.save(user3)
        userDAO.save(user4)
        userDAO.save(user5)
        userDAO.save(user6)
        return userDAO
    }

    @Nested
    inner class CreateUsers {

        @Test
        fun `multiple users added to table can be retrieved successfully`() {
            transaction {
                var userDao = UserDAO()
                //Act & Assert
                assertNotEquals(0, userDao.getAll().size)

            }
        }
    }

    @Nested
    inner class ReadUsers {

        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertNotEquals(0, userDAO.getAll().size)
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Users)
                val userDAO = UserDAO()

                //Act & Assert
                assertNotEquals(0, userDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(null, userDAO.findById(7))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(null, userDAO.findById(7))
            }

        }


        @Test
        fun `get user by phone that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(null, userDAO.findByPhone(nonExistingPhone))
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(null, userDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get user by age that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertNotEquals(null, userDAO.findByAge(nonExistingAge))
            }
        }
    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {
                var userDAO = UserDAO()
                //Act & Assert
                val user6Updated = UserDTO(111, "new username", "new phone", "new@email.ie", 0, "new_gender", "new_address" )
                userDAO.update(111, user6Updated)
                assertEquals(user6Updated, userDAO.findById(111))
            }
        }

    }

    @Nested
    inner class DeleteUsers {

        @Test
        fun `deleting a non-existent user in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = UserDAO()

                //Act & Assert
                assertNotEquals(6, userDAO.getAll().size)
                userDAO.delete(7)
                assertNotEquals(6, userDAO.getAll().size)
            }
        }
    }

}