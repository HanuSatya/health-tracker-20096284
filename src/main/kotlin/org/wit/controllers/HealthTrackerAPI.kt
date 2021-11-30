package org.wit.controllers


import io.javalin.http.Context
import org.wit.repository.UserDAO
import org.wit.repository.ActivityDAO
import org.wit.domain.UserDTO
import org.wit.domain.ActivityDTO
import org.wit.utilities.jsonToObject


object HealthTrackerAPI {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()

    //userDAO

    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(users)
    }

    fun getUserByUserId(ctx: Context)
    {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun getUserByPhoneNumber(ctx: Context)
    {
        val user = userDao.findByPhone(ctx.pathParam("phone"))
        if (user !=null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun getUserByEmail(ctx: Context)
    {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun getUserByAge(ctx: Context)
    {
        val user = userDao.findByAge(ctx.pathParam("age").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun getUsersByGender(ctx: Context)
    {
        val user =  userDao.findByGender(ctx.pathParam("gender"))
        if (user != null) {
            ctx.json(user)
        ctx.status(200)
    }
    else{
        ctx.status(404)
    }
}

    fun getUserByAddress(ctx: Context)
    {
        val user = userDao.findByAddress(ctx.pathParam("address"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addUser(ctx: Context)
    {
        val user : UserDTO = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        }
    }

    fun deleteUser(ctx: Context)
    {
        if (userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)

    }

    fun updateUser(ctx: Context){
        val user : UserDTO = jsonToObject(ctx.body())
        if ((userDao.update(id = ctx.pathParam("user-id").toInt(), userDTO=user)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }



/*
ActivityDAO
*/


    fun getAllActivities(ctx: Context) {
    ctx.json(activityDAO.getAll())
    }

    fun getActivitiesByUserId(ctx: Context) {
    if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
        val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
        if (activities.size > 0)
            ctx.json(activities)
        ctx.status(200)
    }
    else{
        ctx.status(404)
       }
    }

    fun addActivity(ctx: Context)
    {
        val activityDTO : ActivityDTO = jsonToObject(ctx.body())
        val userId = userDao.findById(activityDTO.userId)
        if (userId != null) {
            val activityId = activityDAO.save(activityDTO)
            if (activityId != null) {
                activityDTO.id = activityId
                ctx.json(activityDTO)
                ctx.status(200)
            }
        }
        else{
            ctx.status(404)
        }
    }


    fun getActivitiesByActivityId(ctx: Context) {
    val activity = activityDAO.findByActivityId((ctx.pathParam("activity-id").toInt()))
    if (activity != null){
        ctx.json(activity)
        ctx.status(200)
        }
    else{
        ctx.status(404)
    }
    }


    fun deleteActivityByActivityId(ctx: Context){
        if (activityDAO.deleteByActivityId(ctx.pathParam("activity-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    fun deleteActivityByUserId(ctx: Context){
        if (activityDAO.deleteByUserId(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


    fun updateActivity(ctx: Context){
        val activity : ActivityDTO = jsonToObject(ctx.body())
        if (activityDAO.updateByActivityId(
                activityId = ctx.pathParam("activity-id").toInt(),
                activityDTO=activity) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }


}



