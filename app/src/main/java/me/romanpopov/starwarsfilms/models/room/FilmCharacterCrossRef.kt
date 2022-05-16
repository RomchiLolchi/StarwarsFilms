package me.romanpopov.starwarsfilms.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["episode_id", "character_id"])
data class FilmCharacterCrossRef(
    @ColumnInfo(name = "episode_id")
    val episodeId: Int,
    @ColumnInfo(name = "character_id")
    val characterId: Int,
)