package org.wit.repository


import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

import org.wit.db.Users
import org.wit.domain.UserDTO
import org.wit.utilities.mapToUserDTO


class UserDAO {


    fun getAll() : ArrayList<UserDTO>
    {
        val userList: ArrayList<UserDTO> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUserDTO(it)) }
        }
        return userList
    }


    fun findById(id: Int): UserDTO?
    {
        return null;
    }


    fun findByPhone(phone_number: String): UserDTO?
    {
        return null;
    }


    fun findByEmail(email_id: String): UserDTO?
    {
       return null;
    }


    fun findByAge(age: Int): UserDTO?
    {
       return null;
    }

    fun findByGender(gender: Char): UserDTO?
    {
        return null;
    }

    fun findByAddress(address: String): UserDTO?
    {
        return null;
    }

    fun save(userDTO: UserDTO)
    {

    }

    fun update(id: Int, userDTO: UserDTO)
    {

    }

    fun deleteById(id: Int) {

    }


    }
