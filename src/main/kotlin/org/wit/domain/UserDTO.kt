package org.wit.domain

data class UserDTO (
    var id: Int,
    var full_name: String,
    var phone_number: Long,
    var email_id: String,
    var age: Int,
    var gender: Char,
    var address: String)

