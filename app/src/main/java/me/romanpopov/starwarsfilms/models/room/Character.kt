package me.romanpopov.starwarsfilms.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Character(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    @SerializedName("character_id")
    val characterId: Int,
    val characterName: String,
    val gender: String,
    @ColumnInfo(name = "birth_year")
    @SerializedName("birth_year")
    val dateOfBirth: String,
    @ColumnInfo(name = "homeworld_id")
    @SerializedName("homeworld_id")
    val homeworldId: Int,
) : Serializable
