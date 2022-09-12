package org.wit.helpers

import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime
import org.wit.db.Activities
import org.wit.db.Users
import org.wit.domain.ActivityDTO
import org.wit.domain.UserDTO
import org.wit.repository.*

val nonExistingPhone ="7711xxxxxx"
val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val nonExistingAge = 0
val nonExistingGender = "otherxxx"
val nonExistingAddress = "Dubxxxxxx"

val validName = "Test User 1"
val validPhone = "Test User Ph.No"
val validEmail = "testuser1@test.com"
val validAge = 0
val validGender ="Test User Gender"
val validAddress = "Test Users address"


val users = arrayListOf<UserDTO>(
    UserDTO(id = 1, full_name = "Hanuma Satya Sunnam1", phone_number = "72813231", email_id = "hanuma.255@gmail.com", age = 23, gender = "Male", address = "Dublin,Ireland"),
    UserDTO(id = 2, full_name = "Vamshi Krishna Sunnam1", phone_number = "23267890", email_id = "vamshi.3454@gmail.com", age = 21, gender = "Male", address = "Hyderabad,India"),
    UserDTO(id = 3, full_name = "Srinivasa Chary Sunnam1", phone_number = "327463633", email_id = "sunnam.234@gmail.com", age = 54, gender = "Male", address = "Kakarla,India"),
    UserDTO(id = 4, full_name = "Nagamani Sunnam1", phone_number = "327463263", email_id = "sunnam.231@gmail.com", age = 48, gender = "Female", address = "Nandigama,India"),
    UserDTO(id = 5, full_name = "Vontela Akanksha1", phone_number = "237045907", email_id = "vontela.542@gmail.com", age = 22, gender = "Female", address = "Karimnagar,India"),
    UserDTO(id = 6, full_name = "Ravi Teja1", phone_number = "232343341", email_id = "ravi.123@gmail.com", age = 23, gender = "Male", address = "Kansas,USA")
)

val activities = arrayListOf<ActivityDTO>(
    ActivityDTO(id = 1, description = "Running TEST2342", duration = 22.0, calories = 230, started = DateTime.now(), userId = 2),
    ActivityDTO(id = 2, description = "Hopping2433", duration = 10.5, calories = 80, started = DateTime.now(), userId = 2),
    ActivityDTO(id = 3, description = "Walking34332", duration = 12.0, calories = 120, started = DateTime.now(), userId = 3),
    ActivityDTO(id = 4, description = "Swimming2323", duration = 11.0, calories = 150, started = DateTime.now(), userId = 2),
    ActivityDTO(id = 5, description = "Cycling433", duration = 20.0, calories = 200, started = DateTime.now(), userId = 2),
    ActivityDTO(id = 6, description = "Jumping2343", duration = 10.0, calories = 170, started = DateTime.now(), userId = 3)
)

fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    userDAO.save(users.get(2))
    userDAO.save(users.get(3))
    userDAO.save(users.get(4))
    userDAO.save(users.get(5))
    return userDAO
}

fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities.get(0))
    activityDAO.save(activities.get(1))
    activityDAO.save(activities.get(2))
    activityDAO.save(activities.get(3))
    activityDAO.save(activities.get(4))
    activityDAO.save(activities.get(5))
    return activityDAO
}