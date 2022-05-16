package me.romanpopov.starwarsfilms.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import me.romanpopov.starwarsfilms.models.CharacterAndHomeworld
import me.romanpopov.starwarsfilms.models.room.Character

@Dao
interface CharactersDao {
    @Transaction
    @Query("SELECT * FROM character WHERE character_id = :id")
    fun getById(id: Int): CharacterAndHomeworld

    @Query("SELECT count(*) FROM character WHERE character_id = :id")
    fun checkContains(id: Int): Int

    @Insert
    fun insertAll(vararg characters: Character)
}