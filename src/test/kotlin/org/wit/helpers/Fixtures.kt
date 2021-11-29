package org.wit.helpers

import org.wit.domain.UserDTO

val nonExistingPhone ="xxxxxxxxxx"
val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val nonExistingAge = 0
val nonExistingGender = "otherxxx"
val nonExistingAddress = "xxxxxxxxx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<UserDTO>(
    UserDTO(id = 1, full_name = "Hanuma Satya Sunnam", phone_number = "7729015811", email_id = "hanuma.sathya@gmail.com", age = 23, gender = "Male", address = "Dublin,Ireland"),
    UserDTO(id = 2, full_name = "Vamshi Krishna Sunnam", phone_number = "1234567890", email_id = "vamshi.krishna@gmail.com", age = 21, gender = "Male", address = "Hyderabad,India"),
    UserDTO(id = 3, full_name = "Srinivasa Chary Sunnam", phone_number = "9441746363", email_id = "sunnam.chary@gmail.com", age = 54, gender = "Male", address = "Kakarla,India"),
    UserDTO(id = 4, full_name = "Nagamani Sunnam", phone_number = "8441746363", email_id = "sunnam.nagamani@gmail.com", age = 48, gender = "Female", address = "Nandigama,India"),
    UserDTO(id = 5, full_name = "Vontela Akanksha", phone_number = "7667045907", email_id = "vontela.akanksha@gmail.com", age = 22, gender = "Female", address = "Karimnagar,India"),
    UserDTO(id = 6, full_name = "Ravi Teja", phone_number = "987654321", email_id = "ravi.polishetty@gmail.com", age = 23, gender = "Male", address = "Kansas,USA")
)