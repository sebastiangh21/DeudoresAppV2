package com.sgh21.deudoresappv2.data.dao

import androidx.room.*
import com.sgh21.deudoresappv2.data.entities.User

@Dao
interface UserDao {

    @Insert
    fun createUser(user: User)

    @Query("SELECT * FROM table_user WHERE email LIKE :email")
    fun readUser(email: String) : User
}