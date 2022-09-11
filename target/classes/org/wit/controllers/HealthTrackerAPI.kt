package org.wit.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.http.Context
import org.wit.repository.UserDAO
import org.wit.repository.ActivityDAO
import org.wit.domain.UserDTO
import org.wit.domain.ActivityDTO
import org.wit.domain.AnalyticsDTO
import org.wit.repository.AnalyticsDAO


object HealthTrackerAPI {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()
    private val analyticsDAO = AnalyticsDAO()

               //userDAO//


    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context)
    {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun getUserByPhoneNumber(ctx: Context)
    {
        val user = userDao.findByPhone(ctx.pathParam("phone"))
        if (user !=null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun getUserByEmail(ctx: Context)
    {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun getUserByAge(ctx: Context)
    {
        val user = userDao.findByAge(ctx.pathParam("age").toInt())
        if (user != null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun getUsersByGender(ctx: Context)
    {
        val user =  userDao.findByGender(ctx.pathParam("gender"))
        if (user != null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun getUserByAddress(ctx: Context)
    {
        val user = userDao.findByAddress(ctx.pathParam("address"))
        if (user != null) {
            ctx.json(user)
        }
        else {
            ctx.status(404)
        }
    }

    fun addUser(ctx: Context)
    {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<UserDTO>(ctx.body())
        val userId = userDao.save(user)
        ctx.json(user)
    }

    fun deleteUser(ctx: Context)
    {
        var deleted = userDao.delete(ctx.pathParam("user-id").toInt())
        if (deleted == 1) {
            ctx.status(204)
        }
        else {
            ctx.status(404)
        }

    }

    fun updateUser(ctx: Context){
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<UserDTO>(ctx.body())
        var update = userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            userDTO=user)
        ctx.status(204)
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
       }
    }

    fun addActivity(ctx: Context)
    {
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<ActivityDTO>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }


    fun getActivitiesByActivityId(ctx: Context) {
    val activity = activityDAO.findByActivityId((ctx.pathParam("activity-id").toInt()))
    if (activity != null){
        ctx.json(activity)
    }
    }


    fun deleteActivityByActivityId(ctx: Context){
        activityDAO.deleteByActivityId(ctx.pathParam("activity-id").toInt())
    }


    fun deleteActivityByUserId(ctx: Context){
        activityDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }


    fun updateActivity(ctx: Context){
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<ActivityDTO>(ctx.body())
        activityDAO.updateByActivityId(
            activityId = ctx.pathParam("activity-id").toInt(),
            activityDTO=activity)
    }

    //  AnalyticsDAO

    fun addAnalytics(ctx: Context){
        val mapper = jacksonObjectMapper()
        val analytics = mapper.readValue<AnalyticsDTO>(ctx.body())
        analyticsDAO.save(analytics)
        ctx.json(analytics)
    }

    fun getTableDetails(ctx: Context){
        val analytics = analyticsDAO.getTable()
        ctx.json(analytics)
    }

    fun getAnalytics(ctx: Context){
        val analytics = analyticsDAO.getAnalytics()
        ctx.json(analytics)
    }

    fun getAnalyticsByUserId(ctx: Context){
        val analytics = analyticsDAO.findByUserId(ctx.pathParam("user-id").toInt())
        if (analytics != null){
            ctx.json(analytics)
        }
    }

    fun updateAnalytics(ctx: Context){
        val mapper = jacksonObjectMapper()
        val analytics = mapper.readValue<AnalyticsDTO>(ctx.body())
        analyticsDAO.update(ctx.pathParam("id").toInt(),analytics)
    }
}


