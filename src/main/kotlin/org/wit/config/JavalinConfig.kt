package org.wit.config

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.wit.controllers.HealthTrackerAPI

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
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
            get(   "/api/users/:user-id", HealthTrackerAPI::getUserByUserId)
            get(   "/api/users/phone/:phone", HealthTrackerAPI::getUserByPhoneNumber)
            get(   "/api/users/email/:email", HealthTrackerAPI::getUserByEmail)
            get(   "/api/users/age/:age", HealthTrackerAPI::getUserByAge)
            get(   "/api/users/gender/:gender", HealthTrackerAPI::getUserByGender)
            get(   "/api/users/address/:address", HealthTrackerAPI::getUserByAddress)
            post(  "/api/users", HealthTrackerAPI::addUser)
            delete("/api/users/:user-id", HealthTrackerAPI::deleteUser)
            patch( "/api/users/:user-id", HealthTrackerAPI::updateUser)
        }
    }

}