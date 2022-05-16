package me.romanpopov.starwarsfilms.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import me.romanpopov.starwarsfilms.models.room.Character
import me.romanpopov.starwarsfilms.models.room.Film
import me.romanpopov.starwarsfilms.models.room.FilmCharacterCrossRef
import java.io.Serializable

data class FilmWithCharacters(
    @Embedded
    var film: Film,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "character_id",
        associateBy = Junction(FilmCharacterCrossRef::class),
    )
    val characters: List<Character>,
) : Serializable, Comparable<FilmWithCharacters> {
    override fun compareTo(other: FilmWithCharacters): Int {
        return this.film.episodeId.compareTo(other.film.episodeId)
    }
}