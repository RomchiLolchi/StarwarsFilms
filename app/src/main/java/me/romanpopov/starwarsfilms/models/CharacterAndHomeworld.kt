package me.romanpopov.starwarsfilms.models

import androidx.room.Embedded
import androidx.room.Relation
import me.romanpopov.starwarsfilms.models.room.Character
import me.romanpopov.starwarsfilms.models.room.Planet
import java.io.Serializable

data class CharacterAndHomeworld(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "homeworld_id",
        entityColumn = "planet_id",
    )
    val homeworld: Planet,
) : Serializable