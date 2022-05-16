package me.romanpopov.starwarsfilms.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import me.romanpopov.starwarsfilms.models.FilmWithCharacters
import me.romanpopov.starwarsfilms.models.room.Film

@Dao
interface FilmsDao {
    @Transaction
    @Query("SELECT * FROM film")
    fun getAll(): List<FilmWithCharacters>

    @Insert
    fun insertAll(vararg films: Film)
}