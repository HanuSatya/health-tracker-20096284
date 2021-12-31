package org.wit.config

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.plugin.rendering.vue.VueComponent
import org.wit.controllers.HealthTrackerAPI

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create { config ->
            config.enableWebjars()
        }.start(getHerokuAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun getHerokuAssignedPort(): Int {
        val herokuPort = System.getenv("PORT")
        return if (herokuPort != null) {
            Integer.parseInt(herokuPort)
        } else 7000
    }


    private fun registerRoutes(app: Javalin){
        app.routes {
            get(   "/api/users", HealthTrackerAPI::getAllUsers)
            post(  "/api/users", HealthTrackerAPI::addUser)
            get(   "/api/users/:user-id", HealthTrackerAPI::getUserByUserId)
            get(   "/api/users/phone/:phone", HealthTrackerAPI::getUserByPhoneNumber)
            get(   "/api/users/email/:email", HealthTrackerAPI::getUserByEmail)
            get(   "/api/users/age/:age", HealthTrackerAPI::getUserByAge)
            get(   "/api/users/gender/:gender", HealthTrackerAPI::getUsersByGender)
            get(   "/api/users/address/:address", HealthTrackerAPI::getUserByAddress)
            get("/api/users/:user-id/activities", HealthTrackerAPI::getActivitiesByUserId)
            delete("/api/users/:user-id", HealthTrackerAPI::deleteUser)
            delete("/api/users/:user-id/activities", HealthTrackerAPI::deleteActivityByUserId)
            patch( "/api/users/:user-id", HealthTrackerAPI::updateUser)

            //ACTIVITIES - API CRUD
            get(   "/api/activities", HealthTrackerAPI::getAllActivities)
            get(   "/api/activities/:activity-id", HealthTrackerAPI::getActivitiesByActivityId)
            post(  "/api/activities", HealthTrackerAPI::addActivity)
            delete("/api/activities/:activity-id", HealthTrackerAPI::deleteActivityByActivityId)
            patch( "/api/activities/:activity-id", HealthTrackerAPI::updateActivity)


            // BMI api
            get(   "/api/users/:height/:weight", HealthTrackerAPI::getBMI)

            // VUE api
            get("/", VueComponent("<home-page></home-page>"))
            get("/users", VueComponent("<user-overview></user-overview>"))
            get("/users/:user-id", VueComponent("<user-profile></user-profile>"))
            get("/users/:user-id/activities", VueComponent("<user-activity-overview></user-activity-overview>"))

        }
    }

}