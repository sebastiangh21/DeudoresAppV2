package com.sgh21.deudoresappv2

import android.app.Application
import androidx.room.Room
import com.sgh21.deudoresappv2.data.DeudorDatabase
import com.sgh21.deudoresappv2.data.UserDatabase

class DeudoresApp : Application() {
    companion object{

        lateinit var database: DeudorDatabase
        lateinit var database2: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DeudorDatabase::class.java,
            "deudor_db"
        ).allowMainThreadQueries()
            .build()

        database2 = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "user_db"
        ).allowMainThreadQueries()
            .build()

    }
}