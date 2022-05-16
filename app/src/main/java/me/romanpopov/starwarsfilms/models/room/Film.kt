package me.romanpopov.starwarsfilms.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
data class Film(
    @PrimaryKey
    @ColumnInfo(name = "episode_id")
    @SerializedName("episode_id")
    val episodeId: Int,
    val title: String,
    @ColumnInfo(name = "opening_crawl")
    @SerializedName("opening_crawl")
    val openingText: String,
    val director: String,
    val producer: String,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: Date,
) : Serializable