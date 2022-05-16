package me.romanpopov.starwarsfilms.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.romanpopov.starwarsfilms.models.room.Planet

@Dao
interface PlanetsDao {
    @Query("SELECT * FROM planet WHERE planet_id = :id")
    fun getById(id: Int): Planet

    @Query("SELECT count(*) FROM planet WHERE planet_id = :id")
    fun checkContains(id: Int): Int

    @Insert
    fun insertAll(vararg planets: Planet)
}