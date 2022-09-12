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

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()


                //Act & Assert
                assertEquals(6, userDAO.getAll().size)
                assertEquals(user1, userDAO.findById(user1.id))
                assertEquals(user2, userDAO.findById(user2.id))
                assertEquals(user3, userDAO.findById(user3.id))
                assertEquals(user4, userDAO.findById(user4.id))
                assertEquals(user5, userDAO.findById(user5.id))
                assertEquals(user6, userDAO.findById(user6.id))

            }
        }
    }

    @Nested
    inner class ReadUsers {

        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(6, userDAO.getAll().size)
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Users)
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(0, userDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findById(7))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findById(7))
            }

        }


        @Test
        fun `get user by phone that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByPhone(nonExistingPhone))
            }
        }

        @Test
        fun `get user by phone that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user1, userDAO.findByPhone(user1.phone_number))
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get user by email that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user3, userDAO.findByEmail(user3.email_id))
            }
        }

        @Test
        fun `get user by age that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByAge(nonExistingAge))
            }
        }

        @Test
        fun `get user by age that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user5, userDAO.findByAge(user5.age))
            }
        }

        @Test
        fun `get user by gender that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByGender(nonExistingGender))
            }
        }

        @Test
        fun `get user by gender that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user4, userDAO.findByGender(user4.gender))
            }
        }

        @Test
        fun `get user by address that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByAddress(nonExistingAddress))
            }
        }

        @Test
        fun `get user by address that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user1, userDAO.findByAddress(user1.address))
            }
        }

    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                val user6Updated = UserDTO(6, "new username", "new phone", "new@email.ie", 0, "new_gender", "new_address" )
                userDAO.update(user6.id, user6Updated)
                assertEquals(user6Updated, userDAO.findById(6))
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                val user7Updated = UserDTO(7, "new username", "new phone", "new@email.ie", 0, "new_gender", "new_address")
                userDAO.update(7, user7Updated)
                assertEquals(null, userDAO.findById(7))
                assertEquals(6, userDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteUsers {

        @Test
        fun `deleting a non-existent user in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(6, userDAO.getAll().size)
                userDAO.delete(7)
                assertEquals(6, userDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with six users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(6, userDAO.getAll().size)
                userDAO.delete(user6.id)
                assertEquals(5, userDAO.getAll().size)
            }
        }
    }

}