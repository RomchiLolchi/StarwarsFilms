package me.romanpopov.starwarsfilms.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Planet(
    @PrimaryKey
    @ColumnInfo(name = "planet_id")
    @SerializedName("planet_id")
    val planetId: Int,
    @ColumnInfo(name = "planet_name")
    @SerializedName("planet_name")
    val planetName: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val population: String,
) : Serializable
