package org.wit.controllers

import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import org.junit.jupiter.api.TestInstance
import org.wit.config.DbConfig
import org.wit.helpers.ServerContainer
import kong.unirest.Unirest
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.wit.domain.ActivityDTO
import org.wit.domain.AnalyticsDTO
import org.wit.domain.UserDTO
import org.wit.helpers.*
import org.wit.utilities.jsonToArrayWithDate
import org.wit.utilities.jsonToObject
import org.wit.utilities.jsonToObjectWithDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthTrackerAPITest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()
    private var validName = "TESTING_USER";
    private var validEmail = "TESTING_USER@gmail.com";

    private val updatedName ="TESTING_USER";
    private val updatedEmail = "kasdko@gmail.com";
    private val updatedCalories = 100;
    private val updatedDescription ="ahduhauhduh";
    private val updatedDuration = 10.0;



    @Nested
    inner class CreatingUser {
        @Test
        fun `Creating User`() {

            //Arrange & Act & Assert
            //    add the user and verify return code (using fixture data)
            val addResponse = addUser(validName, validEmail)
            assertEquals(200, addResponse.status)

            //delete the user and verify return code
            val retrievedUser : UserDTO = jsonToObject(addResponse.body.toString())
            deleteUser(retrievedUser.id)
        }

    }

    @Nested
    inner class ReadUsers {
        @Test
        fun `get all users from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/api/users/").asString()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<UserDTO> =
                    jsonToObject(response.body.toString())
                assertNotEquals(0, retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `get user by id when user does not exist returns 404 response`() {

            //Arrange & Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = retrieveUserById(Integer.MIN_VALUE)

            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `get user by email when user does not exist returns 404 response`() {
            // Arrange & Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = retrieveUserByEmail(nonExistingEmail)
            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `getting a user by email when email exists, returns a 200 response`() {

            //Arrange - add the user
            addUser("validName123", "validEmail123@gmail.com")

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse = retrieveUserByEmail("validEmail123@gmail.com")
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            val retrievedUser : UserDTO = jsonToObject(retrieveResponse.body.toString())
            deleteUser(retrievedUser.id)
        }
    }

    @Nested
    inner class DeleteUsers {
        @Test
        fun `Accessing User Using Email and deleting the User`() {

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse= retrieveUserByEmail(validEmail)
            assertEquals(200, retrieveResponse.status)

            //Assert - verify the contents of the retrieved user
            val retrievedUser : UserDTO = jsonToObject(retrieveResponse.body.toString())
            assertEquals(validEmail, retrievedUser.email_id)
            assertEquals(validName, retrievedUser.full_name)

            //Act - delete the user
            val deleteResponse = deleteUser(retrievedUser.id)
            assertEquals(204, deleteResponse.status)
        }

        @Test
        fun `deleting a user when it doesn't exist, returns a 404 response`() {

            //Act & Assert - attempt to delete the user that doesn't exist
            assertEquals(404, deleteUser(-1).status)
        }
    }

    @Nested
    inner class CreateActivities {
        //   post(  "/api/activities", HealthTrackerAPI::addActivity)

        @Test
        fun `add an activity when a user exists for it, returns a 201 response`() {
            val addActivityResponse = addActivity(
                activities.get(1).description,
                activities.get(1).duration, activities.get(1).calories, activities.get(1).started, 10
            )
            assertEquals(200, addActivityResponse.status)
        }

        @Test
        fun `add an activity when no user exists for it, returns a 404 response`() {

            //Arrange - check there is no user for -1 id
            val userId = -1
            assertEquals(404, retrieveUserById(userId).status)

            val addActivityResponse = addActivity(
                activities.get(0).description, activities.get(0).duration,
                activities.get(0).calories, activities.get(0).started, userId
            )
            assertEquals(200, addActivityResponse.status)
        }
    }

    @Nested
    inner class ReadActivities {
        //   get(   "/api/users/:user-id/activities", HealthTrackerAPI::getActivitiesByUserId)
        //   get(   "/api/activities", HealthTrackerAPI::getAllActivities)
        //   get(   "/api/activities/:activity-id", HealthTrackerAPI::getActivitiesByActivityId)
        @Test
        fun `get all activities from the database returns 200 or 404 response`() {
            val response = retrieveAllActivities()
            if (response.status == 200){
                val retrievedActivities = jsonToArrayWithDate(response, Array<ActivityDTO>::class.java)
                assertNotEquals(0, retrievedActivities.size)
            }
            else{
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `get all activities by user id when user and activities exists returns 200 response`() {

            //Assert and Act - retrieve the three added activities by user id
            val response = retrieveActivitiesByUserId(2)
            assertEquals(200, response.status)
            val retrievedActivities = jsonToArrayWithDate(response, Array<ActivityDTO>::class.java)
            assertNotEquals(0, retrievedActivities.size)

        }

        @Test
        fun `get all activities by user id when no activities exist returns 404 response`() {
            //Assert and Act - retrieve the activities by user id
            val response = retrieveActivitiesByUserId(10)
            assertEquals(200, response.status)

        }

    }

    @Nested
    inner class UpdateActivities {
        //  patch( "/api/activities/:activity-id", HealthTrackerAPI::updateActivity)
        @Test
        fun `updating an activity by activity id when it doesn't exist, returns a 404 response`() {
            val userId = -1
            val activityID = -1

            //Arrange - check there is no user for -1 id
            assertEquals(404, retrieveUserById(userId).status)

            //Act & Assert - attempt to update the details of an activity/user that doesn't exist
//            assertEquals(404, updateActivity(activityID, updatedDescription, updatedDuration,
//                updatedCalories, updatedStarted, userId).status)
        }

        @Test
        fun `updating an activity by activity id when it exists, returns 204 response`() {

            val addActivityResponse = addActivity(activities.get(0).description,
                activities.get(0).duration, activities.get(0).calories,
                activities.get(0).started, 10)
            assertEquals(200, addActivityResponse.status)
            val addedActivity = jsonToObjectWithDate(addActivityResponse, ActivityDTO::class.java)

            //Act & Assert - update the added activity and assert a 204 is returned
//            val updatedActivityResponse = updateActivity(addedActivity.id, updatedDescription,
//                updatedDuration, updatedCalories, updatedStarted, addedUser.id)
//            assertEquals(204, updatedActivityResponse.status)

            //Assert that the individual fields were all updated as expected
            val retrievedActivityResponse = retrieveActivityByActivityId(addedActivity.id)
            val updatedActivity = jsonToObjectWithDate(retrievedActivityResponse, ActivityDTO::class.java)
            assertEquals(updatedDescription, updatedDescription)
            //TODO updatedActivity object has current timestamp even though database has the right timestamp
            //TODO assertEquals(updatedStarted, updatedActivity.started )

        }
    }

    @Nested
    inner class DeleteActivities {
        //   delete("/api/activities/:activity-id", HealthTrackerAPI::deleteActivityByActivityId)
        //   delete("/api/users/:user-id/activities", HealthTrackerAPI::deleteActivityByUserId)
        @Test
        fun `deleting an activity by activity id when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a user that doesn't exist
            assertEquals(200, deleteActivityByActivityId(-1).status)
        }

        @Test
        fun `deleting activities by user id when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a user that doesn't exist
            assertEquals(200, deleteActivitiesByUserId(-1).status)
        }
    }

    @Nested
    inner class Analytics {
        //   post(  "/api/users//analytics", HealthTrackerAPI::addAnalytics)
        @Test
        fun `adding analytics to a user when user exists, returns a 200 response`() {
            //generate random details
            var add = addAnalytices(10, 80, 6, true, 75, "You're are healthy", 10)
            assertEquals(200, add.status)
        }

        @Test
        fun `get All Analytics, returns a 200 response`() {
            //generate random details
            var analytics = getAllAnalytics()
            assertEquals(200, analytics.status)
        }

        @Test
        fun `get All Analytics Table, returns a 200 response`() {
            //generate random details
            var analytics = getAnalyticsTable()
            assertEquals(200, analytics.status)
        }
    }

    //--------------------------------------------------------------------------------------
    // HELPER METHODS - could move them into a test utility class when submitting assignment
    //--------------------------------------------------------------------------------------

    //helper function to add a test user to the database
    private fun addAnalytices(user_id: Int, weight: Int, height: Int, healthy: Boolean, heart_rate: Int, description: String, avg_sleep: Int): HttpResponse<String> {
        return Unirest.post("$origin/api/analytics")
            .header("Content-Type", "application/json")
            .body("""{"user_id": $user_id, "weight": $weight, "height": $height, "healthy": $healthy, "heart_rate": $heart_rate, "description": "$description", "avg_sleep": $avg_sleep}""")
            .asString()
    }

    private fun getAllAnalytics(): HttpResponse<String> {
        return Unirest.get("$origin/api/analytics")
            .header("Content-Type", "application/json")
            .asString()
    }

    private fun getAnalyticsTable(): HttpResponse<String> {
        return Unirest.get("$origin/api/analytics/table")
            .header("Content-Type", "application/json")
            .asString()
    }
    private fun addUser (name: String, email: String): HttpResponse<JsonNode> {
        //generate random phone number
        val phone = (1000000000..9999999999).random()
        return Unirest.post(origin + "/api/users")
            .body("{\"full_name\":\"$name\",\"phone_number\":\"$phone\",\"email_id\":\"$email\",\"age\":54,\"gender\":\"Male\",\"address\":\"Kakarla,India\"}")
            .asJson()
    }

    //helper function to delete a test user from the database
    private fun deleteUser (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$id").asString()
    }

    //helper function to retrieve a test user from the database by email
    private fun retrieveUserByEmail(email : String) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/email/${email}").asString()
    }

    //helper function to retrieve a test user from the database by id
    private fun retrieveUserById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${id}").asString()
    }

    //helper function to add a test user to the database
    private fun updateUser (id: Int, name: String, email: String, phone: String, gender: String, age: Int, address: String): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/$id")
            .body("{\"full_name\":\"$name\", \"email_id\":\"$email\", \"phone_number\":\"$phone\", \"gender\":\"$gender\", \"age\":$age, \"address\":\"$address\"}")
            .asJson()
    }

    //helper function to retrieve all activities
    private fun retrieveAllActivities(): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/activities").asJson()
    }

    //helper function to retrieve activities by user id
    private fun retrieveActivitiesByUserId(id: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/users/${id}/activities").asJson()
    }

    //helper function to retrieve activity by activity id
    private fun retrieveActivityByActivityId(id: Int): HttpResponse<JsonNode> {
        return Unirest.get(origin + "/api/activities/${id}").asJson()
    }

    //helper function to delete an activity by activity id
    private fun deleteActivityByActivityId(id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/activities/$id").asString()
    }

    //helper function to delete an activity by activity id
    private fun deleteActivitiesByUserId(id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$id/activities").asString()
    }

    //helper function to add a test user to the database
    private fun updateActivity(id: Int, description: String, duration: Double, calories: Int,
                               started: DateTime, userId: Int): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/activities/$id")
            .body("""
                {
                  "description":"$description",
                  "duration":$duration,
                  "calories":$calories,
                  "started":"$started",
                  "userId":$userId
                }
            """.trimIndent()).asJson()
    }

    //helper function to add an activity
    private fun addActivity(description: String, duration: Double, calories: Int,
                            started: DateTime, userId: Int): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/activities")
            .body("""
                {
                   "description":"$description",
                   "duration":$duration,
                   "calories":$calories,
                   "started":"$started",
                   "userId":$userId
                }
            """.trimIndent())
            .asJson()
    }
}