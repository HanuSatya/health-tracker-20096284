package org.wit.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.wit.db.Users
import org.wit.domain.UserDTO
import org.wit.utilities.mapToUserDTO


class UserDAO {

    fun getAll(): ArrayList<UserDTO> {
        val userList: ArrayList<UserDTO> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUserDTO(it))
            }
        }
        return userList
    }


    fun findById(id: Int): UserDTO? {
        return transaction {
            Users.select() {
                Users.id eq id
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }


    fun findByPhone(phone_number: String): UserDTO? {
        return transaction {
            Users.select() {
                Users.phone_number eq phone_number
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }

    fun findByEmail(email_id: String): UserDTO? {
        return transaction {
            Users.select() {
                Users.email_id eq email_id
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }


    fun findByAge(age: Int): UserDTO? {
        return transaction {
            Users.select() {
                Users.age eq age
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }

    fun findByGender(gender: String): UserDTO? {
        return transaction {
            Users.select() {
                Users.gender eq gender
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }

    fun findByAddress(address: String): UserDTO? {
        return transaction {
            Users.select() {
                Users.address eq address
            }
                .map { mapToUserDTO(it) }
                .firstOrNull()
        }
    }

    fun save(userDTO: UserDTO): Int?
    {
        return transaction {
            Users.insert {
                it[full_name] = userDTO.full_name
                it[phone_number] = userDTO.phone_number
                it[email_id] = userDTO.email_id
                it[age] = userDTO.age
                it[gender] = userDTO.gender
                it[address] = userDTO.address
            } get Users.id
        }
    }

    fun update(id: Int, userDTO: UserDTO): Int {
        return transaction {
            Users.update({
                Users.id eq id })
            {
                it[full_name] = userDTO.full_name
                it[phone_number] = userDTO.phone_number
                it[email_id] = userDTO.email_id
                it[age] = userDTO.age
                it[gender] = userDTO.gender
                it[address] = userDTO.address
            }
        }
    }

    fun delete(id: Int): Int {
        return transaction {
            Users.deleteWhere { Users.id eq id }
        }
    }

}
