package org.wit.config

import org.jetbrains.exposed.sql.Database


class DbConfig{


    //NOTE: you need the ?sslmode=require otherwise you get an error complaining about the ssl certificate
    fun getDbConnection() :Database{

        return Database.connect(
            "jdbc:postgresql://ec2-54-221-74-111.compute-1.amazonaws.com:5432/d45ep7ga1tbhhk?sslmode=require",
            driver = "org.postgresql.Driver",
            user = "mksllimlbcetcr",
            password = "05b76274aa7f4940997675f77f1862064be5ef82d991fa76c2f3f971dd107db3")

    }

}