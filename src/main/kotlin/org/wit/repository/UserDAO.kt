package org.wit.repository

import org.wit.domain.UserDTO

class UserDAO {

    private val users = arrayListOf<UserDTO>(
        UserDTO(id = 0, full_name = "Hanuma Satya Sunnam", phone_number = 7729015811, email_id = "hanuma.sathya@gmail.com",age = 23, gender = 'M', address = "Dublin,Ireland"  ),
        UserDTO(id = 1, full_name = "Vamshi Krishna Sunnam", phone_number = 8994766472, email_id = "vamshi.krish@gmail.com",age = 21, gender = 'M', address = "Hyderabad,India" ),
        UserDTO(id = 2, full_name = "Srinivasa Chary Sunnam", phone_number = 94417463636, email_id = "sunnam.chary@gmail.com",age = 54, gender = 'M', address = "Kakarla,India" ),
        UserDTO(id = 3, full_name = "Nagamani Sunnam", phone_number = 8374224505, email_id = "nagamani.sunnam@gmail.com",age = 48, gender = 'F', address = "Nandigama,India" )
    )

    fun getAll() : ArrayList<UserDTO>
    {
        return users
    }


    fun findById(id: Int): UserDTO?
    {
        return users.find {
            it.id == id
        }
    }


    fun findByPhone(phone_number: Long): UserDTO?
    {
        return users.find {
            it.phone_number == phone_number
        }
    }


    fun findByEmail(email_id: String):UserDTO?
    {
        return users.find {
            it.email_id == email_id
        }
    }


    fun findByAge(age: Int):UserDTO?
    {
        return users.find {
            it.age == age
        }
    }

    fun findByGender(gender: Char?):UserDTO?
    {
        return users.find {
            it.gender == gender
        }
    }

    fun save(userDTO: UserDTO)
    {
        users.add(userDTO)
    }


    fun deleteById(id: Int)
    {
        val user = findById(id)
        users.remove(user)
    }


    fun update(id: Int, userDTO: UserDTO){
        val user = findById(id)
        user?.id = userDTO.id
        user?.full_name = userDTO.full_name
        user?.phone_number = userDTO.phone_number
        user?.email_id = userDTO.email_id
        user?.age = userDTO.age
        user?.gender = userDTO.gender
        if (user?.gender in listOf('F', 'M', 'O'))
            userDTO.gender = user?.gender!!
        user?.address = userDTO.address
    }


}