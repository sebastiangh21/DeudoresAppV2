package com.sgh21.deudoresappv2.data.dao

import androidx.room.*
import com.sgh21.deudoresappv2.data.entities.Deudor

@Dao
interface DeudorDao {

    @Insert
    fun createDeudor(deudor: Deudor)

    @Query("SELECT * FROM table_deudor")
    fun getDeudores() : MutableList<Deudor>

    @Query("SELECT * FROM table_deudor WHERE name LIKE :name")
    fun readDeudor(name: String) : Deudor

    @Delete
    fun deleteDeudor(deudor: Deudor)

    @Update
    fun updateDeudor(deudor: Deudor)
}