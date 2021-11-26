package org.wit.domain

data class UserDTO(
    var id: Int,
    var full_name: String,
    var phone_number: String,
    var email_id: String,
    var age: Int,
    var gender: String,
    var address: String)

