package me.romanpopov.starwarsfilms.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.romanpopov.starwarsfilms.database.dao.CharactersDao
import me.romanpopov.starwarsfilms.database.dao.FilmsDao
import me.romanpopov.starwarsfilms.database.dao.PlanetsDao
import me.romanpopov.starwarsfilms.models.room.Character
import me.romanpopov.starwarsfilms.models.room.Film
import me.romanpopov.starwarsfilms.models.room.FilmCharacterCrossRef
import me.romanpopov.starwarsfilms.models.room.Planet
import me.romanpopov.starwarsfilms.utils.RoomConverters

@TypeConverters(value = [RoomConverters::class])
@Database(
    entities = [Film::class, Character::class, Planet::class, FilmCharacterCrossRef::class],
    version = 1
)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
    abstract fun charactersDao(): CharactersDao
    abstract fun planetsDao(): PlanetsDao
}