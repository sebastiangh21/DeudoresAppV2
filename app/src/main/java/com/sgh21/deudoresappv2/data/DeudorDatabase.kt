package com.sgh21.deudoresappv2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sgh21.deudoresappv2.data.dao.DeudorDao
import com.sgh21.deudoresappv2.data.entities.Deudor

@Database(entities = [Deudor::class], version = 1)
abstract class DeudorDatabase: RoomDatabase() {
    abstract fun DeudorDao(): DeudorDao
}