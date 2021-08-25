package com.sgh21.deudoresappv2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sgh21.deudoresappv2.data.dao.UserDao
import com.sgh21.deudoresappv2.data.entities.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun UserDao(): UserDao
}